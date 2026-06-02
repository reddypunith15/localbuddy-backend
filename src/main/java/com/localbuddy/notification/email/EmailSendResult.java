package com.localbuddy.notification.email;

public record EmailSendResult(
        boolean success,
        String providerMessageId,
        String failureReason
) {
}