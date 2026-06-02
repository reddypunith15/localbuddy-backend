package com.localbuddy.payment;

import com.localbuddy.ratelimit.ClientIpResolver;
import com.localbuddy.ratelimit.RateLimitService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/public/guest-payments")
public class PublicGuestPaymentController {

    private final PaymentService paymentService;
    private final RateLimitService rateLimitService;
    private final ClientIpResolver clientIpResolver;

    public PublicGuestPaymentController(PaymentService paymentService,
                                        RateLimitService rateLimitService,
                                        ClientIpResolver clientIpResolver) {
        this.paymentService = paymentService;
        this.rateLimitService = rateLimitService;
        this.clientIpResolver = clientIpResolver;
    }

    @PostMapping
    public ResponseEntity<PaymentResponse> createPendingGuestPayment(
            HttpServletRequest servletRequest,
            @Valid @RequestBody CreateGuestPaymentRequest request
    ) {
        String clientIp = clientIpResolver.resolveClientIp(servletRequest);
        rateLimitService.checkPublicApiLimit("guest-payment:" + clientIp);

        PaymentResponse response = paymentService.createPendingGuestPayment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/lookup")
    public ResponseEntity<PaymentResponse> lookupGuestPayment(
            HttpServletRequest servletRequest,
            @Valid @RequestBody GuestPaymentLookupRequest request
    ) {
        String clientIp = clientIpResolver.resolveClientIp(servletRequest);
        rateLimitService.checkPublicApiLimit("guest-payment-lookup:" + clientIp);

        return ResponseEntity.ok(paymentService.lookupGuestPayment(request));
    }


    @PostMapping("/checkout")
    public ResponseEntity<PaymentCheckoutResponse> createGuestCheckout(
            HttpServletRequest servletRequest,
            @Valid @RequestBody CreateGuestPaymentRequest request
    ) {
        String clientIp = clientIpResolver.resolveClientIp(servletRequest);
        rateLimitService.checkPublicApiLimit("guest-payment-checkout:" + clientIp);

        PaymentCheckoutResponse response = paymentService.createGuestCheckout(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}