CREATE TABLE payments (
                          id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

                          booking_id UUID NOT NULL,

                          provider VARCHAR(40) NOT NULL DEFAULT 'STRIPE',
                          payment_method_type VARCHAR(60) NOT NULL DEFAULT 'UNKNOWN',
                          payment_status VARCHAR(40) NOT NULL DEFAULT 'PENDING',

                          amount NUMERIC(10,2) NOT NULL,
                          currency VARCHAR(3) NOT NULL DEFAULT 'EUR',

                          platform_fee_amount NUMERIC(10,2) NOT NULL DEFAULT 0,
                          local_payout_amount NUMERIC(10,2) NOT NULL DEFAULT 0,

                          provider_checkout_session_id VARCHAR(255),
                          provider_payment_intent_id VARCHAR(255),
                          provider_charge_id VARCHAR(255),
                          provider_customer_id VARCHAR(255),
                          provider_payment_method_id VARCHAR(255),

                          checkout_url TEXT,

                          failure_reason TEXT,
                          refund_reason TEXT,

                          paid_at TIMESTAMPTZ,
                          failed_at TIMESTAMPTZ,
                          cancelled_at TIMESTAMPTZ,
                          refunded_at TIMESTAMPTZ,

                          created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
                          updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),

                          CONSTRAINT fk_payments_booking
                              FOREIGN KEY (booking_id)
                                  REFERENCES bookings(id),

                          CONSTRAINT chk_payments_amount_non_negative
                              CHECK (amount >= 0),

                          CONSTRAINT chk_payments_platform_fee_non_negative
                              CHECK (platform_fee_amount >= 0),

                          CONSTRAINT chk_payments_local_payout_non_negative
                              CHECK (local_payout_amount >= 0),

                          CONSTRAINT chk_payments_fee_plus_payout_valid
                              CHECK (platform_fee_amount + local_payout_amount <= amount)
);

CREATE UNIQUE INDEX ux_payments_booking_active
    ON payments (booking_id)
    WHERE payment_status IN ('PENDING', 'PROCESSING', 'PAID');

CREATE UNIQUE INDEX ux_payments_provider_checkout_session_id
    ON payments (provider, provider_checkout_session_id)
    WHERE provider_checkout_session_id IS NOT NULL;

CREATE UNIQUE INDEX ux_payments_provider_payment_intent_id
    ON payments (provider, provider_payment_intent_id)
    WHERE provider_payment_intent_id IS NOT NULL;

CREATE UNIQUE INDEX ux_payments_provider_charge_id
    ON payments (provider, provider_charge_id)
    WHERE provider_charge_id IS NOT NULL;

CREATE INDEX idx_payments_booking_id ON payments (booking_id);
CREATE INDEX idx_payments_provider ON payments (provider);
CREATE INDEX idx_payments_method_type ON payments (payment_method_type);
CREATE INDEX idx_payments_status ON payments (payment_status);
CREATE INDEX idx_payments_created_at ON payments (created_at);

CREATE TABLE payment_webhook_events (
                                        id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

                                        provider VARCHAR(40) NOT NULL DEFAULT 'STRIPE',
                                        provider_event_id VARCHAR(255) NOT NULL,
                                        event_type VARCHAR(120) NOT NULL,

                                        processed BOOLEAN NOT NULL DEFAULT FALSE,
                                        processing_error TEXT,

                                        received_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
                                        processed_at TIMESTAMPTZ,

                                        raw_payload TEXT,

                                        CONSTRAINT ux_payment_webhook_provider_event
                                            UNIQUE (provider, provider_event_id)
);

CREATE INDEX idx_payment_webhook_events_provider ON payment_webhook_events (provider);
CREATE INDEX idx_payment_webhook_events_event_type ON payment_webhook_events (event_type);
CREATE INDEX idx_payment_webhook_events_processed ON payment_webhook_events (processed);
CREATE INDEX idx_payment_webhook_events_received_at ON payment_webhook_events (received_at);