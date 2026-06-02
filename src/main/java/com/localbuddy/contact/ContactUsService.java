package com.localbuddy.contact;

import com.localbuddy.common.exception.BadRequestException;
import com.localbuddy.notification.NotificationService;
import com.localbuddy.notification.NotificationType;
import com.localbuddy.user.User;
import com.localbuddy.user.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

@Service
public class ContactUsService {

    private final NotificationService notificationService;
    private final UserRepository userRepository;
    private final String supportEmail;

    public ContactUsService(NotificationService notificationService,
                            UserRepository userRepository,
                            @Value("${app.support.email:admin@test.com}") String supportEmail) {
        this.notificationService = notificationService;
        this.userRepository = userRepository;
        this.supportEmail = supportEmail;
    }

    @Transactional
    public ContactUsResponse submitContactUs(
            ContactUsRequest request,
            String ipAddress,
            String userAgent
    ) {
        String normalizedSupportEmail = requiredTrim(supportEmail).toLowerCase(Locale.ROOT);

        User adminUser = userRepository.findByEmail(normalizedSupportEmail)
                .orElseThrow(() -> new BadRequestException("Support admin user is not configured"));

        String subject = "Contact Us: " + requiredTrim(request.subject());

        String body = """
                New Contact Us message received.
                
                Name: %s
                Email: %s
                Subject: %s
                
                Message:
                %s
                
                IP Address: %s
                User-Agent: %s
                """.formatted(
                requiredTrim(request.name()),
                requiredTrim(request.email()),
                requiredTrim(request.subject()),
                requiredTrim(request.message()),
                optionalTrim(ipAddress),
                optionalTrim(userAgent)
        );

        notificationService.createEmailNotificationForUser(
                adminUser,
                NotificationType.SYSTEM_ALERT,
                subject,
                body,
                "CONTACT_US",
                null,
                "CONTACT_US:" + normalizedSupportEmail + ":" + System.currentTimeMillis()
        );

        return new ContactUsResponse(
                "Thanks for contacting LocalBuddy. Our team will get back to you soon."
        );
    }

    private String requiredTrim(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new BadRequestException("Required value is missing");
        }
        return value.trim();
    }

    private String optionalTrim(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        return value.trim();
    }
}