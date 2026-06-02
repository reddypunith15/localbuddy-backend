package com.localbuddy.trustsafety;

import jakarta.validation.constraints.Size;

public record UpdateSafetyReportRequest(
        SafetyReportStatus status,
        SafetyReportSeverity severity,

        @Size(max = 5000, message = "Admin notes cannot exceed 5000 characters")
        String adminNotes
) {
}