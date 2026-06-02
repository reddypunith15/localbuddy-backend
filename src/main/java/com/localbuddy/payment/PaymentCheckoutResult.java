package com.localbuddy.payment;

public record PaymentCheckoutResult(
        String checkoutUrl,
        String providerCheckoutSessionId,
        String providerPaymentIntentId,
        PaymentMethodType paymentMethodType
) {
}