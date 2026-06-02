CREATE TABLE IF NOT EXISTS cancellation_refund_policies (
                                                            id UUID PRIMARY KEY,
                                                            name VARCHAR(150) NOT NULL,

    cancelled_by VARCHAR(40) NOT NULL,

    min_hours_before_start NUMERIC(8, 2) NOT NULL,
    max_hours_before_start NUMERIC(8, 2),

    refund_percentage NUMERIC(5, 2) NOT NULL,

    active BOOLEAN NOT NULL DEFAULT TRUE,

    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL
    );

CREATE INDEX IF NOT EXISTS idx_cancellation_refund_policies_cancelled_by
    ON cancellation_refund_policies(cancelled_by);

CREATE INDEX IF NOT EXISTS idx_cancellation_refund_policies_active
    ON cancellation_refund_policies(active);


INSERT INTO cancellation_refund_policies (
    id,
    name,
    cancelled_by,
    min_hours_before_start,
    max_hours_before_start,
    refund_percentage,
    active,
    created_at,
    updated_at
)
VALUES
    ('11111111-1111-1111-1111-111111111101', 'Traveler cancellation - 24+ hours before start', 'TRAVELER', 24.00, NULL, 100.00, TRUE, NOW(), NOW()),
    ('11111111-1111-1111-1111-111111111102', 'Traveler cancellation - 12 to 24 hours before start', 'TRAVELER', 12.00, 24.00, 50.00, TRUE, NOW(), NOW()),
    ('11111111-1111-1111-1111-111111111103', 'Traveler cancellation - 2 to 12 hours before start', 'TRAVELER', 2.00, 12.00, 25.00, TRUE, NOW(), NOW()),
    ('11111111-1111-1111-1111-111111111104', 'Traveler cancellation - less than 2 hours before start', 'TRAVELER', 0.00, 2.00, 0.00, TRUE, NOW(), NOW()),

    ('11111111-1111-1111-1111-111111111201', 'Local cancellation - full refund', 'LOCAL', 0.00, NULL, 100.00, TRUE, NOW(), NOW()),
    ('11111111-1111-1111-1111-111111111301', 'Admin cancellation - full refund', 'ADMIN', 0.00, NULL, 100.00, TRUE, NOW(), NOW())
    ON CONFLICT (id) DO NOTHING;


ALTER TABLE payments
    ADD COLUMN IF NOT EXISTS provider_refund_id VARCHAR(255),
    ADD COLUMN IF NOT EXISTS refunded_amount NUMERIC(10, 2);