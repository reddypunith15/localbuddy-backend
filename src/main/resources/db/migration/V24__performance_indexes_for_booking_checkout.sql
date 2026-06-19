CREATE INDEX IF NOT EXISTS idx_bookings_traveler_slot_status
ON bookings(traveler_user_id, availability_slot_id, status);

CREATE INDEX IF NOT EXISTS idx_bookings_local_profile_status_requested
ON bookings(local_profile_id, status, requested_at DESC);

CREATE INDEX IF NOT EXISTS idx_bookings_status_requested
ON bookings(status, requested_at DESC);

CREATE INDEX IF NOT EXISTS idx_payments_booking_status_created
ON payments(booking_id, payment_status, created_at DESC);

CREATE INDEX IF NOT EXISTS idx_payments_provider_checkout_session
ON payments(provider, provider_checkout_session_id);

CREATE INDEX IF NOT EXISTS idx_payment_webhook_events_provider_event
ON payment_webhook_events(provider, provider_event_id);

CREATE INDEX IF NOT EXISTS idx_promo_redemptions_promo_user
ON promo_code_redemptions(promo_code_id, user_id);

CREATE INDEX IF NOT EXISTS idx_promo_redemptions_promo_guest
ON promo_code_redemptions(promo_code_id, LOWER(guest_email));

CREATE INDEX IF NOT EXISTS idx_promo_redemptions_booking
ON promo_code_redemptions(booking_id);

CREATE INDEX IF NOT EXISTS idx_user_account_restrictions_user_type_active
ON user_account_restrictions(user_id, restriction_type, active);