package com.localbuddy.admin;

import com.localbuddy.payment.PaymentResponse;
import com.localbuddy.payment.PaymentService;
import com.localbuddy.payment.PaymentStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/payments")
public class AdminPaymentController {

    private final PaymentService paymentService;

    public AdminPaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping
    public ResponseEntity<List<PaymentResponse>> getAdminPayments(
            @RequestParam(required = false) PaymentStatus status
    ) {
        return ResponseEntity.ok(paymentService.getAdminPayments(status));
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentResponse> getAdminPaymentById(
            @PathVariable UUID paymentId
    ) {
        return ResponseEntity.ok(paymentService.getAdminPaymentById(paymentId));
    }
}