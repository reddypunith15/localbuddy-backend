package com.localbuddy.booking;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class BookingReferenceGenerator {

    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("yyyyMMdd");

    private final BookingReferenceSequenceRepository sequenceRepository;

    public BookingReferenceGenerator(BookingReferenceSequenceRepository sequenceRepository) {
        this.sequenceRepository = sequenceRepository;
    }

    @Transactional
    public String generate() {
        LocalDate today = LocalDate.now();

        BookingReferenceSequence sequence = sequenceRepository
                .findByBookingDateForUpdate(today)
                .orElseGet(() -> sequenceRepository.saveAndFlush(new BookingReferenceSequence(today)));

        sequence.increment();

        return today.format(DATE_FORMATTER) + String.format("%04d", sequence.getLastSequence());
    }
}