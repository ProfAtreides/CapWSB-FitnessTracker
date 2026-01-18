package pl.wsb.fitnesstracker.training.internal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
@Slf4j
public class ReportController {

    private final WeeklyTrainingReportService reportService;

    @PostMapping("/generate")
    public ResponseEntity<String> generateReports() {
        log.info("Manual report generation triggered via API");
        try {
            reportService.generateWeeklyReports();
            return ResponseEntity.ok("Weekly training reports generated and sent successfully!");
        } catch (Exception e) {
            log.error("Error generating reports manually", e);
            return ResponseEntity.internalServerError()
                    .body("Error generating reports: " + e.getMessage());
        }
    }
}

