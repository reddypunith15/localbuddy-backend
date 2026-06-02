package com.localbuddy.payment;

import java.math.BigDecimal;

public record PaymentRefundResult(
        String providerRefundId,
        PaymentStatus paymentStatus,
        BigDecimal refundedAmount
) {
}