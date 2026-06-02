package com.localbuddy.payment;

import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.net.Webhook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/public/payments/webhooks/stripe")
public class StripeWebhookController {

    private static final Logger log = LoggerFactory.getLogger(StripeWebhookController.class);

    private final PaymentService paymentService;
    private final StripeProperties stripeProperties;

    public StripeWebhookController(PaymentService paymentService,
                                   StripeProperties stripeProperties) {
        this.paymentService = paymentService;
        this.stripeProperties = stripeProperties;
    }

    @PostMapping
    public ResponseEntity<StripeWebhookResponse> handleStripeWebhook(
            @RequestBody String payload,
            @RequestHeader("Stripe-Signature") String stripeSignature
    ) {
        log.info("Stripe webhook received. Payload length={}, signaturePresent={}, webhookSecretConfigured={}",
                payload != null ? payload.length() : 0,
                stripeSignature != null && !stripeSignature.isBlank(),
                stripeProperties.webhookSecret() != null && !stripeProperties.webhookSecret().isBlank()
        );

        if (stripeProperties.webhookSecret() == null ||
                stripeProperties.webhookSecret().trim().isEmpty()) {
            throw new IllegalStateException("Stripe webhook secret is not configured");
        }

        Event event;
        try {
            event = Webhook.constructEvent(
                    payload,
                    stripeSignature,
                    stripeProperties.webhookSecret().trim()
            );
            log.info("Stripe webhook signature verified. eventId={}, eventType={}",
                    event.getId(),
                    event.getType()
            );
        } catch (SignatureVerificationException ex) {
            log.warn("Invalid Stripe webhook signature: {}", ex.getMessage());
            throw new IllegalArgumentException("Invalid Stripe webhook signature");
        }

        StripeWebhookResponse response = paymentService.handleStripeWebhookEvent(event, payload);
        return ResponseEntity.ok(response);
    }
}