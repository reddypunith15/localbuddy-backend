package com.localbuddy.trustsafety;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record CreateSafetyReportRequest(

        @NotNull(message = "Booking id is required")
        UUID bookingId,

        @NotNull(message = "Report type is required")
        SafetyReportType reportType,

        @NotNull(message = "Severity is required")
        SafetyReportSeverity severity,

        @NotBlank(message = "Description is required")
        @Size(max = 5000, message = "Description cannot exceed 5000 characters")
        String description
) {
}