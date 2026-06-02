package com.localbuddy.referral;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/public/referrals")
public class PublicReferralController {

    private final ReferralService referralService;

    public PublicReferralController(ReferralService referralService) {
        this.referralService = referralService;
    }

    @PostMapping("/validate")
    public ResponseEntity<ValidateReferralCodeResponse> validateGuestReferralCode(
            @Valid @RequestBody ValidateReferralCodeRequest request
    ) {
        return ResponseEntity.ok(referralService.validateReferralCode(null, request));
    }
}