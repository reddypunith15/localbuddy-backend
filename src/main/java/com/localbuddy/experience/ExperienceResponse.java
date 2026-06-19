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
        UUID cityId,
        String cityName,
        String citySlug,
        String country,
        String title,
        String slug,
        String description,
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