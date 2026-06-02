package com.localbuddy.payment;

import java.math.BigDecimal;
import java.util.UUID;

public record PaymentCheckoutResponse(
        UUID paymentId,
        UUID bookingId,
        PaymentProvider provider,
        PaymentMethodType paymentMethodType,
        PaymentStatus paymentStatus,
        BigDecimal amount,
        String currency,
        String checkoutUrl
) {
}