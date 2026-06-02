package com.localbuddy.admin;

public record AdminDashboardSummaryResponse(
        long totalUsers,
        long totalLocalProfiles,
        long pendingLocalProfiles,
        long approvedLocalProfiles,
        long totalExperiences,
        long pendingExperiences,
        long approvedExperiences,
        long totalBookings,
        long requestedBookings,
        long acceptedBookings,
        long cancelledBookings,
        long openSafetyReports,
        long inReviewSafetyReports,
        long visibleReviews,
        long hiddenReviews
) {
}