package com.localbuddy.payment;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<PaymentResponse> createPendingPayment(
            Authentication authentication,
            @Valid @RequestBody CreatePaymentRequest request
    ) {
        UUID userId = UUID.fromString(authentication.getName());
        PaymentResponse response = paymentService.createPendingPayment(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentResponse> getPaymentById(
            Authentication authentication,
            @PathVariable UUID paymentId
    ) {
        UUID userId = UUID.fromString(authentication.getName());
        return ResponseEntity.ok(paymentService.getPaymentById(userId, paymentId));
    }

    @PostMapping("/checkout")
    public ResponseEntity<PaymentCheckoutResponse> createCheckout(
            Authentication authentication,
            @Valid @RequestBody CreatePaymentRequest request
    ) {
        UUID userId = UUID.fromString(authentication.getName());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(paymentService.createCheckout(userId, request));
    }
}