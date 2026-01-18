package pl.wsb.fitnesstracker.mail.internal;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.mail.javamail.JavaMailSender;
import pl.wsb.fitnesstracker.mail.api.EmailSender;

@ConfigurationProperties(prefix = "mail")
@Getter
@RequiredArgsConstructor
class MailProperties {

    private final String from;

}
