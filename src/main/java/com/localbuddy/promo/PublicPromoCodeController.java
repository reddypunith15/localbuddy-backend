package com.localbuddy.promo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/public/promo-codes")
@Tag(name = "Public - Promo Codes", description = "Public endpoints for validating promo codes (no authentication required)")
public class PublicPromoCodeController {

    private final PromoCodeService promoCodeService;

    public PublicPromoCodeController(PromoCodeService promoCodeService) {
        this.promoCodeService = promoCodeService;
    }

    @Operation(
            summary = "Validate a promo code (guest)",
            description = "Validates a promo code for a guest user. No authentication required."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Promo code validation result returned"),
            @ApiResponse(responseCode = "400", description = "Invalid request body")
    })
    @PostMapping("/validate")
    public ResponseEntity<ValidatePromoCodeResponse> validateGuestPromoCode(
            @Valid @RequestBody ValidatePromoCodeRequest request
    ) {
        return ResponseEntity.ok(
                promoCodeService.validatePromoCode(null, request)
        );
    }
}