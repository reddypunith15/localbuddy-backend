package com.localbuddy.referral;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ReferralCodeRepository extends JpaRepository<ReferralCode, UUID> {

    Optional<ReferralCode> findByCodeIgnoreCase(String code);

    Optional<ReferralCode> findByOwnerUserId(UUID ownerUserId);

    boolean existsByCodeIgnoreCase(String code);

    boolean existsByOwnerUserId(UUID ownerUserId);
}