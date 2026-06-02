package com.localbuddy.payment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record StripeWebhookRequest(

        @NotBlank(message = "Provider event id is required")
        @Size(max = 255, message = "Provider event id cannot exceed 255 characters")
        String providerEventId,

        @NotBlank(message = "Event type is required")
        @Size(max = 120, message = "Event type cannot exceed 120 characters")
        String eventType,

        @Size(max = 255, message = "Checkout session id cannot exceed 255 characters")
        String providerCheckoutSessionId,

        @Size(max = 255, message = "Payment intent id cannot exceed 255 characters")
        String providerPaymentIntentId,

        PaymentMethodType paymentMethodType,

        String rawPayload
) {
}