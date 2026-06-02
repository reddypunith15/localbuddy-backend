CREATE TABLE IF NOT EXISTS trust_safety_reports (
                                                    id UUID PRIMARY KEY,

                                                    reporter_user_id UUID,
                                                    reported_user_id UUID,
                                                    booking_id UUID,

                                                    report_type VARCHAR(60) NOT NULL,
    severity VARCHAR(40) NOT NULL,
    status VARCHAR(40) NOT NULL,

    description TEXT NOT NULL,
    admin_notes TEXT,

    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL,
    resolved_at TIMESTAMPTZ,

    CONSTRAINT fk_trust_safety_reports_reporter_user
    FOREIGN KEY (reporter_user_id)
    REFERENCES users(id),

    CONSTRAINT fk_trust_safety_reports_reported_user
    FOREIGN KEY (reported_user_id)
    REFERENCES users(id),

    CONSTRAINT fk_trust_safety_reports_booking
    FOREIGN KEY (booking_id)
    REFERENCES bookings(id)
    );

CREATE INDEX IF NOT EXISTS idx_trust_safety_reports_reporter_user_id
    ON trust_safety_reports(reporter_user_id);

CREATE INDEX IF NOT EXISTS idx_trust_safety_reports_reported_user_id
    ON trust_safety_reports(reported_user_id);

CREATE INDEX IF NOT EXISTS idx_trust_safety_reports_booking_id
    ON trust_safety_reports(booking_id);

CREATE INDEX IF NOT EXISTS idx_trust_safety_reports_status
    ON trust_safety_reports(status);

CREATE INDEX IF NOT EXISTS idx_trust_safety_reports_severity
    ON trust_safety_reports(severity);


CREATE TABLE IF NOT EXISTS user_account_restrictions (
                                                         id UUID PRIMARY KEY,

                                                         user_id UUID NOT NULL,
                                                         restriction_type VARCHAR(60) NOT NULL,

    reason TEXT NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,

    created_by_admin_user_id UUID,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL,
    deactivated_at TIMESTAMPTZ,

    CONSTRAINT fk_user_account_restrictions_user
    FOREIGN KEY (user_id)
    REFERENCES users(id),

    CONSTRAINT fk_user_account_restrictions_admin_user
    FOREIGN KEY (created_by_admin_user_id)
    REFERENCES users(id)
    );

CREATE INDEX IF NOT EXISTS idx_user_account_restrictions_user_id
    ON user_account_restrictions(user_id);

CREATE INDEX IF NOT EXISTS idx_user_account_restrictions_type
    ON user_account_restrictions(restriction_type);

CREATE INDEX IF NOT EXISTS idx_user_account_restrictions_active
    ON user_account_restrictions(active);