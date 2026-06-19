package com.localbuddy.payment;

import com.localbuddy.ratelimit.ClientIpResolver;
import com.localbuddy.ratelimit.RateLimitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/public/guest-payments")
@Tag(name = "Public - Guest Payments", description = "Public, rate-limited endpoints for guest payments (no authentication required)")
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

    @Operation(
            summary = "Create a pending guest payment",
            description = "Creates a pending payment for a guest user. No authentication required. Rate-limited per client IP."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Pending guest payment created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body"),
            @ApiResponse(responseCode = "429", description = "Too many requests (rate limit exceeded)")
    })
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

    @Operation(
            summary = "Look up a guest payment",
            description = "Looks up an existing guest payment by the provided lookup details. No authentication required. Rate-limited per client IP."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Guest payment retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body"),
            @ApiResponse(responseCode = "404", description = "Guest payment not found"),
            @ApiResponse(responseCode = "429", description = "Too many requests (rate limit exceeded)")
    })
    @PostMapping("/lookup")
    public ResponseEntity<PaymentResponse> lookupGuestPayment(
            HttpServletRequest servletRequest,
            @Valid @RequestBody GuestPaymentLookupRequest request
    ) {
        String clientIp = clientIpResolver.resolveClientIp(servletRequest);
        rateLimitService.checkPublicApiLimit("guest-payment-lookup:" + clientIp);

        return ResponseEntity.ok(paymentService.lookupGuestPayment(request));
    }

    @Operation(
            summary = "Create a guest checkout",
            description = "Creates a checkout session for a guest payment. No authentication required. Rate-limited per client IP."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Guest checkout created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body"),
            @ApiResponse(responseCode = "429", description = "Too many requests (rate limit exceeded)")
    })
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