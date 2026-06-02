package com.localbuddy.promo;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/public/promo-codes")
public class PublicPromoCodeController {

    private final PromoCodeService promoCodeService;

    public PublicPromoCodeController(PromoCodeService promoCodeService) {
        this.promoCodeService = promoCodeService;
    }

    @PostMapping("/validate")
    public ResponseEntity<ValidatePromoCodeResponse> validateGuestPromoCode(
            @Valid @RequestBody ValidatePromoCodeRequest request
    ) {
        return ResponseEntity.ok(
                promoCodeService.validatePromoCode(null, request)
        );
    }
}