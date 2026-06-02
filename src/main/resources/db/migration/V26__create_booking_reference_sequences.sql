CREATE TABLE IF NOT EXISTS booking_reference_sequences (
                                                           booking_date DATE PRIMARY KEY,
                                                           last_sequence INTEGER NOT NULL DEFAULT 0,
                                                           created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                                           updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);