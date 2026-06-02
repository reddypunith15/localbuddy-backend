package com.localbuddy.notification.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(
        name = "app.email.provider",
        havingValue = "console",
        matchIfMissing = true
)
public class ConsoleEmailProviderService implements EmailProviderService {

    private static final Logger log = LoggerFactory.getLogger(ConsoleEmailProviderService.class);

    private final EmailProperties emailProperties;

    public ConsoleEmailProviderService(EmailProperties emailProperties) {
        this.emailProperties = emailProperties;
    }

    @Override
    public EmailSendResult sendEmail(EmailSendRequest request) {
        log.info("""
                
                ===== LOCALBUDDY EMAIL - CONSOLE PROVIDER =====
                From: {} <{}>
                To: {}
                Subject: {}
                Message:
                {}
                =================================================
                """,
                emailProperties.fromName(),
                emailProperties.fromAddress(),
                request.toEmail(),
                request.subject(),
                request.message()
        );

        return new EmailSendResult(
                true,
                "console-" + System.currentTimeMillis(),
                null
        );
    }
}