package com.localbuddy.safety;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BookingSafetyChecklistRepository extends JpaRepository<BookingSafetyChecklist, UUID> {

    Optional<BookingSafetyChecklist> findByBookingIdAndUserId(UUID bookingId, UUID userId);

    boolean existsByBookingIdAndUserIdAndCompletedTrue(UUID bookingId, UUID userId);
}