package com.localbuddy.safety;

import java.time.Instant;
import java.util.UUID;

public record BookingSafetyChecklistResponse(
        UUID id,
        UUID bookingId,
        UUID userId,
        BookingSafetyRoleContext roleContext,
        boolean publicMeetingAcknowledged,
        boolean communicationGuidelinesAcknowledged,
        boolean personalSafetyAcknowledged,
        boolean reportingGuidelinesAcknowledged,
        boolean completed,
        Instant completedAt,
        Instant createdAt,
        Instant updatedAt
) {
}