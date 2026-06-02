package com.localbuddy.localprofile;

import java.util.UUID;

public record LocalOnboardingStatusResponse(
        boolean hasProfile,
        UUID localProfileId,
        LocalApprovalStatus approvalStatus,
        LocalVerificationStatus verificationStatus,
        boolean canEdit,
        boolean canSubmit,
        boolean canCreateExperience,
        String message
) {
}