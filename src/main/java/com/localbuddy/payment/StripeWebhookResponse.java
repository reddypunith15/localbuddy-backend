package com.localbuddy.payment;

import java.util.UUID;

public record StripeWebhookResponse(
        UUID id,
        PaymentProvider provider,
        String providerEventId,
        String eventType,
        boolean processed,
        String message
) {
}