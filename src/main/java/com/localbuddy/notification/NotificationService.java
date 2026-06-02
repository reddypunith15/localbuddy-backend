package com.localbuddy.notification;

import com.localbuddy.user.User;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Transactional
    public void createEmailNotificationForUser(
            User recipientUser,
            NotificationType notificationType,
            String subject,
            String message,
            String relatedEntityType,
            UUID relatedEntityId,
            String dedupeKey
    ) {
        if (recipientUser == null || recipientUser.getEmail() == null) {
            return;
        }

        createNotification(
                recipientUser,
                recipientUser.getEmail(),
                recipientUser.getPhone(),
                NotificationChannel.EMAIL,
                notificationType,
                subject,
                message,
                relatedEntityType,
                relatedEntityId,
                dedupeKey
        );
    }

    @Transactional
    public void createEmailNotificationForGuest(
            String recipientEmail,
            String recipientPhone,
            NotificationType notificationType,
            String subject,
            String message,
            String relatedEntityType,
            UUID relatedEntityId,
            String dedupeKey
    ) {
        if (recipientEmail == null || recipientEmail.trim().isEmpty()) {
            return;
        }

        createNotification(
                null,
                recipientEmail.trim().toLowerCase(),
                recipientPhone,
                NotificationChannel.EMAIL,
                notificationType,
                subject,
                message,
                relatedEntityType,
                relatedEntityId,
                dedupeKey
        );
    }

    private void createNotification(
            User recipientUser,
            String recipientEmail,
            String recipientPhone,
            NotificationChannel channel,
            NotificationType notificationType,
            String subject,
            String message,
            String relatedEntityType,
            UUID relatedEntityId,
            String dedupeKey
    ) {
        if (notificationRepository.existsByDedupeKey(dedupeKey)) {
            return;
        }

        Notification notification = new Notification();
        notification.setRecipientUser(recipientUser);
        notification.setRecipientEmail(optionalTrim(recipientEmail));
        notification.setRecipientPhone(optionalTrim(recipientPhone));
        notification.setChannel(channel);
        notification.setNotificationType(notificationType);
        notification.setSubject(optionalTrim(subject));
        notification.setMessage(message.trim());
        notification.setStatus(NotificationStatus.PENDING);
        notification.setDedupeKey(dedupeKey);
        notification.setRelatedEntityType(relatedEntityType);
        notification.setRelatedEntityId(relatedEntityId);

        try {
            notificationRepository.save(notification);
        } catch (DataIntegrityViolationException ex) {
            // Another request may have inserted the same dedupe key.
            // Safe to ignore because duplicate notification should not be created.
        }
    }

    private String optionalTrim(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        return value.trim();
    }
}