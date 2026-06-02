package com.localbuddy.booking;

import java.math.BigDecimal;

public record RefundCalculationResult(
        BookingCancellationActor cancelledBy,
        BigDecimal hoursBeforeStart,
        BigDecimal refundPercentage,
        BigDecimal refundAmount
) {
}