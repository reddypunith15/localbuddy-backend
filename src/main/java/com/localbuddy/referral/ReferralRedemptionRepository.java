package com.localbuddy.referral;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReferralRedemptionRepository extends JpaRepository<ReferralRedemption, UUID> {

    boolean existsByReferralCodeIdAndReferredUserId(UUID referralCodeId, UUID referredUserId);

    boolean existsByReferralCodeIdAndReferredGuestEmailIgnoreCase(UUID referralCodeId, String referredGuestEmail);

    long countByReferralCodeId(UUID referralCodeId);

    boolean existsByBookingId(UUID bookingId);
}