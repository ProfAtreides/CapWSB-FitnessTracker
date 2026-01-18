package pl.wsb.fitnesstracker.mail.internal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import pl.wsb.fitnesstracker.mail.api.EmailDto;
import pl.wsb.fitnesstracker.mail.api.EmailSender;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailSenderImpl implements EmailSender {

    private final JavaMailSender mailSender;

    @Override
    public void send(EmailDto email) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email.toAddress());
            message.setSubject(email.subject());
            message.setText(email.content());

            mailSender.send(message);
            log.info("Email sent successfully to: {}", email.toAddress());
        } catch (Exception e) {
            log.error("Failed to send email to: {}", email.toAddress(), e);
            throw new RuntimeException("Failed to send email", e);
        }
    }
}
