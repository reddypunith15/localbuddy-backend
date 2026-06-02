package com.localbuddy.safety;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record CreateSafetyReportRequest(

        UUID reportedUserId,

        UUID bookingId,

        @NotNull(message = "Report type is required")
        SafetyReportType reportType,

        @NotNull(message = "Severity is required")
        SafetySeverity severity,

        @NotBlank(message = "Description is required")
        @Size(max = 3000, message = "Description cannot exceed 3000 characters")
        String description
) {
}