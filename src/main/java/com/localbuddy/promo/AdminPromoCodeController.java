package com.localbuddy.promo;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/promo-codes")
public class AdminPromoCodeController {

    private final PromoCodeService promoCodeService;

    public AdminPromoCodeController(PromoCodeService promoCodeService) {
        this.promoCodeService = promoCodeService;
    }

    @PostMapping
    public ResponseEntity<PromoCodeResponse> createPromoCode(
            @Valid @RequestBody CreatePromoCodeRequest request
    ) {
        PromoCodeResponse response = promoCodeService.createPromoCode(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<PromoCodeResponse>> listPromoCodes() {
        return ResponseEntity.ok(promoCodeService.listPromoCodes());
    }

    @GetMapping("/{promoCodeId}")
    public ResponseEntity<PromoCodeResponse> getPromoCode(
            @PathVariable UUID promoCodeId
    ) {
        return ResponseEntity.ok(promoCodeService.getPromoCode(promoCodeId));
    }
}