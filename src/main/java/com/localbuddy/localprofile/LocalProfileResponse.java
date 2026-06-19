package com.localbuddy.localprofile;

import com.localbuddy.experience.CityResponse;
import com.localbuddy.experience.ExperienceCategoryResponse;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record LocalProfileResponse(
        UUID id,
        UUID userId,

        String displayName,
        String phoneNumber,
        String bio,
        String profilePhotoUrl,
        String hostCity,
        String zipCode,
        String country,

        List<String> experienceLanguages,
        List<CityResponse> experienceCities,
        List<ExperienceCategoryResponse> experienceCategories,

        String motivation,
        String experienceInfo,

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
        String currentAddress,

        String accountNumber,
        String accountName,
        String swiftCode,

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
