package com.localbuddy.booking;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record CancellationRefundPolicyResponse(
        UUID id,
        String name,
        BookingCancellationActor cancelledBy,
        BigDecimal minHoursBeforeStart,
        BigDecimal maxHoursBeforeStart,
        BigDecimal refundPercentage,
        boolean active,
        Instant createdAt,
        Instant updatedAt
) {
}