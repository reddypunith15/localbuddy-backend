package com.localbuddy.localprofile;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record LocalProfileResponse(
        UUID id,
        UUID userId,

        String displayName,
        String bio,
        String city,
        String country,
        List<String> languages,
        List<String> interests,
        String occupation,
        String profilePhotoUrl,

        LocalVerificationStatus verificationStatus,
        LocalApprovalStatus approvalStatus,

        String adminReviewNote,
        String rejectionReason,
        String changesRequestedReason,
        Instant reviewedAt,
        Instant submittedAt,
        Instant resubmittedAt,

        String legalFirstName,
        String legalLastName,
        String preferredName,
        String currentCity,
        String currentAddress,
        String buddyCity,

        String verificationProvider,
        String verificationReferenceId,
        Instant verificationStartedAt,
        Instant verificationCompletedAt,
        String verificationFailureReason,

        BigDecimal ratingAvg,
        Integer totalReviews,

        Instant createdAt,
        Instant updatedAt
) {
}