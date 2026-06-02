package com.localbuddy.notification.email;

public interface EmailProviderService {

    EmailSendResult sendEmail(EmailSendRequest request);
}