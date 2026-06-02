package com.localbuddy.payment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PaymentWebhookEventRepository extends JpaRepository<PaymentWebhookEvent, UUID> {

    boolean existsByProviderAndProviderEventId(
            PaymentProvider provider,
            String providerEventId
    );

    Optional<PaymentWebhookEvent> findByProviderAndProviderEventId(
            PaymentProvider provider,
            String providerEventId
    );
}