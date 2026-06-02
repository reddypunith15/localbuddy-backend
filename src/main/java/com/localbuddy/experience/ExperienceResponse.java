package com.localbuddy.experience;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record ExperienceResponse(
        UUID id,
        UUID localProfileId,
        UUID categoryId,
        String categoryName,
        String categorySlug,
        String title,
        String slug,
        String description,
        String city,
        String country,
        String meetingArea,
        Integer durationMinutes,
        BigDecimal priceAmount,
        String currency,
        Integer maxGuests,
        String safetyNotes,
        ExperienceStatus status,
        Instant createdAt,
        Instant updatedAt
) {
}