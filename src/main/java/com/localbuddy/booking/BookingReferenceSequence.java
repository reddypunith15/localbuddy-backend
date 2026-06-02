package com.localbuddy.booking;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "booking_reference_sequences")
public class BookingReferenceSequence {

    @Id
    @Column(name = "booking_date", nullable = false)
    private LocalDate bookingDate;

    @Column(name = "last_sequence", nullable = false)
    private int lastSequence;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    protected BookingReferenceSequence() {
    }

    public BookingReferenceSequence(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
        this.lastSequence = 0;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public int getLastSequence() {
        return lastSequence;
    }

    public void increment() {
        this.lastSequence++;
        this.updatedAt = LocalDateTime.now();
    }
}