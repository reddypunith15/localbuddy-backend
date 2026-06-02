package com.localbuddy.trustsafety;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface UserAccountRestrictionRepository extends JpaRepository<UserAccountRestriction, UUID> {

    boolean existsByUserIdAndRestrictionTypeAndActiveTrue(UUID userId, UserRestrictionType restrictionType);

    List<UserAccountRestriction> findByUserIdOrderByCreatedAtDesc(UUID userId);

    List<UserAccountRestriction> findAllByActiveTrueOrderByCreatedAtDesc();

    boolean existsByUserIdAndRestrictionTypeInAndActiveTrue(
            UUID userId,
            Collection<UserRestrictionType> restrictionTypes
    );
}