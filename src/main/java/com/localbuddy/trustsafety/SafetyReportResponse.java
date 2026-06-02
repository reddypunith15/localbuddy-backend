package com.localbuddy.trustsafety;

import java.time.Instant;
import java.util.UUID;

public record SafetyReportResponse(
        UUID id,
        UUID reporterUserId,
        UUID reportedUserId,
        UUID bookingId,
        SafetyReportType reportType,
        SafetyReportSeverity severity,
        SafetyReportStatus status,
        String description,
        String adminNotes,
        Instant createdAt,
        Instant updatedAt,
        Instant resolvedAt
) {
}