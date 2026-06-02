package com.localbuddy.booking;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Optional;

public interface BookingReferenceSequenceRepository
        extends JpaRepository<BookingReferenceSequence, LocalDate> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
            select s
            from BookingReferenceSequence s
            where s.bookingDate = :bookingDate
            """)
    Optional<BookingReferenceSequence> findByBookingDateForUpdate(LocalDate bookingDate);
}