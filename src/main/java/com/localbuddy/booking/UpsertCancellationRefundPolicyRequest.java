package com.localbuddy.booking;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record UpsertCancellationRefundPolicyRequest(

        @NotBlank(message = "Policy name is required")
        @Size(max = 150, message = "Policy name cannot exceed 150 characters")
        String name,

        @NotNull(message = "Cancelled by is required")
        BookingCancellationActor cancelledBy,

        @NotNull(message = "Minimum hours before start is required")
        @DecimalMin(value = "0.00", message = "Minimum hours before start cannot be negative")
        BigDecimal minHoursBeforeStart,

        @DecimalMin(value = "0.00", message = "Maximum hours before start cannot be negative")
        BigDecimal maxHoursBeforeStart,

        @NotNull(message = "Refund percentage is required")
        @DecimalMin(value = "0.00", message = "Refund percentage cannot be negative")
        @DecimalMax(value = "100.00", message = "Refund percentage cannot exceed 100")
        BigDecimal refundPercentage,

        Boolean active
) {
}