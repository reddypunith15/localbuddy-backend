package com.localbuddy.promo;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/promo-codes")
public class PromoCodeController {

    private final PromoCodeService promoCodeService;

    public PromoCodeController(PromoCodeService promoCodeService) {
        this.promoCodeService = promoCodeService;
    }

    @PostMapping("/validate")
    public ResponseEntity<ValidatePromoCodeResponse> validatePromoCode(
            Authentication authentication,
            @Valid @RequestBody ValidatePromoCodeRequest request
    ) {
        UUID userId = authentication != null
                ? UUID.fromString(authentication.getName())
                : null;

        return ResponseEntity.ok(
                promoCodeService.validatePromoCode(userId, request)
        );
    }
}