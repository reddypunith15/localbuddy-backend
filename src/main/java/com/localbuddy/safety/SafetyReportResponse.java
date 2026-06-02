package com.localbuddy.safety;

import java.time.Instant;
import java.util.UUID;

public record SafetyReportResponse(
        UUID id,
        UUID reporterUserId,
        UUID reportedUserId,
        UUID bookingId,
        SafetyReportType reportType,
        SafetySeverity severity,
        SafetyReportStatus status,
        String description,
        String adminNotes,
        String resolutionNote,
        Instant createdAt,
        Instant updatedAt,
        Instant resolvedAt
) {
}