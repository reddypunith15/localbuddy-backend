package com.localbuddy.experience;

import java.util.UUID;

public record CityResponse(
        UUID id,
        String name,
        String slug,
        String country,
        boolean active,
        Integer displayOrder
) {
}
