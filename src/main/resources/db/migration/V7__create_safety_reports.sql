CREATE TABLE safety_reports (
                                id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

                                reporter_user_id UUID NOT NULL,
                                reported_user_id UUID,
                                booking_id UUID,

                                report_type VARCHAR(50) NOT NULL,
                                severity VARCHAR(40) NOT NULL DEFAULT 'MEDIUM',
                                status VARCHAR(40) NOT NULL DEFAULT 'OPEN',

                                description TEXT NOT NULL,
                                admin_notes TEXT,
                                resolution_note TEXT,

                                created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
                                updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
                                resolved_at TIMESTAMPTZ,

                                CONSTRAINT fk_safety_reports_reporter_user
                                    FOREIGN KEY (reporter_user_id)
                                        REFERENCES users(id),

                                CONSTRAINT fk_safety_reports_reported_user
                                    FOREIGN KEY (reported_user_id)
                                        REFERENCES users(id),

                                CONSTRAINT fk_safety_reports_booking
                                    FOREIGN KEY (booking_id)
                                        REFERENCES bookings(id)
);

CREATE INDEX idx_safety_reports_reporter_user_id ON safety_reports (reporter_user_id);
CREATE INDEX idx_safety_reports_reported_user_id ON safety_reports (reported_user_id);
CREATE INDEX idx_safety_reports_booking_id ON safety_reports (booking_id);
CREATE INDEX idx_safety_reports_report_type ON safety_reports (report_type);
CREATE INDEX idx_safety_reports_severity ON safety_reports (severity);
CREATE INDEX idx_safety_reports_status ON safety_reports (status);
CREATE INDEX idx_safety_reports_created_at ON safety_reports (created_at);