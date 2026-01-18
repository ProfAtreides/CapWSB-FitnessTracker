package pl.wsb.fitnesstracker.training.internal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.wsb.fitnesstracker.mail.api.EmailDto;
import pl.wsb.fitnesstracker.mail.api.EmailSender;
import pl.wsb.fitnesstracker.training.api.Training;
import pl.wsb.fitnesstracker.training.api.TrainingRepository;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.user.api.UserProvider;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class WeeklyTrainingReportService {

    private final TrainingRepository trainingRepository;
    private final UserProvider userProvider;
    private final EmailSender emailSender;

    // TODO CHANGE TO WEEKLY INSTEAD OF EVERY MINUTE AFTER TESTING
    //@Scheduled(cron = "0 0 8 * * MON")
    @Scheduled(cron = "0 */1 * * * *")
    public void generateWeeklyReports() {
        log.info("Starting weekly training report generation");

        LocalDate endDate = LocalDate.now();
        //LocalDate startDate = endDate.minusWeeks(1);
        LocalDate startDate = endDate.minusYears(5); // TODO TESTING DON`T COMMIT

        List<User> allUsers = userProvider.findAllUsers();
        List<Training> allTrainings = trainingRepository.findAll();

        // Group trainings by user
        Map<Long, List<Training>> trainingsByUser = allTrainings.stream()
                .filter(training -> isInDateRange(training, startDate, endDate))
                .collect(Collectors.groupingBy(training -> Objects.requireNonNull(training.getUser().getId())));

        // Generate report for each user
        allUsers.forEach(user -> generateReportForUser(user, trainingsByUser.get(user.getId()), startDate, endDate));

        log.info("Weekly training report generation completed");
    }

    private void generateReportForUser(User user, List<Training> trainings, LocalDate startDate, LocalDate endDate) {
        int trainingCount = trainings != null ? trainings.size() : 0;

        // Log to console
        String consoleReport = generateConsoleReport(user, trainings, startDate, endDate);
        log.info(consoleReport);

        // Send email
        sendEmailReport(user, trainingCount, startDate, endDate);
    }

    private String generateConsoleReport(User user, List<Training> trainings, LocalDate startDate, LocalDate endDate) {
        StringBuilder report = new StringBuilder();
        report.append("\n========== Weekly report ==========\n");
        report.append(String.format("User: %s %s (%s)\n",
                user.getFirstName(), user.getLastName(), user.getEmail()));
        report.append(String.format("Time peroid: %s - %s\n", startDate, endDate));
        report.append("========================================\n");

        if (trainings == null || trainings.isEmpty()) {
            report.append("No trainings in given time period.\n");
        } else {
            report.append(String.format("Weekly result: %d\n", trainings.size()));
            report.append("----------------------------------------\n");
            trainings.forEach(training -> {
                report.append(String.format("- %s: %s (distance: %.2f km)\n",
                        training.getStartTime(),
                        training.getActivityType(),
                        training.getDistance()));
            });
        }

        report.append("========================================\n");
        return report.toString();
    }

    private void sendEmailReport(User user, int trainingCount, LocalDate startDate, LocalDate endDate) {
        try {
            String subject = String.format("Weekly trainings report - %s", endDate);
            String content = generateEmailContent(user, trainingCount, startDate, endDate);

            EmailDto email = new EmailDto(user.getEmail(), subject, content);
            emailSender.send(email);

            log.info("Email report sent to: {}", user.getEmail());
        } catch (Exception e) {
            log.error("Failed to send email report to: {}", user.getEmail(), e);
        }
    }

    private String generateEmailContent(User user, int trainingCount, LocalDate startDate, LocalDate endDate) {
        return String.format("""
                Hi %s %s!
                
                Your weekly trainigs: %s - %s
                
                Number of trainings: %d
                """,
                user.getFirstName(),
                user.getLastName(),
                startDate,
                endDate,
                trainingCount
        );
    }

    private boolean isInDateRange(Training training, LocalDate startDate, LocalDate endDate) {
        LocalDate trainingDate = training.getStartTime().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        return !trainingDate.isBefore(startDate) && !trainingDate.isAfter(endDate);
    }
}

