package com.localbuddy.user;

import java.time.Instant;
import java.util.UUID;

public record UserResponse(
        UUID id,
        String fullName,
        String email,
        String phone,
        UserRole role,
        UserStatus status,
        boolean emailVerified,
        boolean phoneVerified,
        Instant createdAt,
        Instant updatedAt
) {
}