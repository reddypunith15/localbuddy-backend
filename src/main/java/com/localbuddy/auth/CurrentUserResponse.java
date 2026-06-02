package com.localbuddy.auth;

import com.localbuddy.user.UserRole;
import com.localbuddy.user.UserStatus;

import java.util.UUID;

public record CurrentUserResponse(
        UUID userId,
        String fullName,
        String email,
        String phone,
        UserRole role,
        UserStatus status,
        boolean emailVerified,
        boolean phoneVerified
) {
}