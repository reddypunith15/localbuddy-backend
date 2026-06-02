package com.localbuddy.availability;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AvailabilitySlotRepository extends JpaRepository<AvailabilitySlot, UUID> {

    List<AvailabilitySlot> findByLocalProfileIdOrderByStartTimeAsc(UUID localProfileId);

    List<AvailabilitySlot> findByExperienceIdAndStatusAndStartTimeAfterOrderByStartTimeAsc(
            UUID experienceId,
            AvailabilityStatus status,
            Instant startTime
    );

    List<AvailabilitySlot> findByExperienceIdAndStatusOrderByStartTimeAsc(
            UUID experienceId,
            AvailabilityStatus status
    );

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select slot from AvailabilitySlot slot where slot.id = :slotId")
    Optional<AvailabilitySlot> findByIdForUpdate(@Param("slotId") UUID slotId);
}