CREATE TABLE notifications (
                               id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

                               recipient_user_id UUID,
                               recipient_email VARCHAR(255),
                               recipient_phone VARCHAR(40),

                               channel VARCHAR(40) NOT NULL,
                               notification_type VARCHAR(80) NOT NULL,

                               subject VARCHAR(255),
                               message TEXT NOT NULL,

                               status VARCHAR(40) NOT NULL DEFAULT 'PENDING',

                               dedupe_key VARCHAR(255) NOT NULL UNIQUE,

                               related_entity_type VARCHAR(80),
                               related_entity_id UUID,

                               provider_message_id VARCHAR(255),
                               failure_reason TEXT,

                               created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
                               updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
                               sent_at TIMESTAMPTZ,

                               CONSTRAINT fk_notifications_recipient_user
                                   FOREIGN KEY (recipient_user_id)
                                       REFERENCES users(id),

                               CONSTRAINT chk_notifications_recipient_exists
                                   CHECK (
                                       recipient_user_id IS NOT NULL
                                           OR recipient_email IS NOT NULL
                                           OR recipient_phone IS NOT NULL
                                       )
);

CREATE INDEX idx_notifications_recipient_user_id ON notifications (recipient_user_id);
CREATE INDEX idx_notifications_recipient_email ON notifications (recipient_email);
CREATE INDEX idx_notifications_channel ON notifications (channel);
CREATE INDEX idx_notifications_type ON notifications (notification_type);
CREATE INDEX idx_notifications_status ON notifications (status);
CREATE INDEX idx_notifications_related_entity ON notifications (related_entity_type, related_entity_id);
CREATE INDEX idx_notifications_created_at ON notifications (created_at);