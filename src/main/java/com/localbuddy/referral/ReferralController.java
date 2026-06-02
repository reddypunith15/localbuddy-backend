package com.localbuddy.referral;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/referrals")
public class ReferralController {

    private final ReferralService referralService;

    public ReferralController(ReferralService referralService) {
        this.referralService = referralService;
    }

    @PostMapping("/my-code")
    public ResponseEntity<ReferralCodeResponse> getOrCreateMyReferralCode(
            Authentication authentication
    ) {
        UUID userId = UUID.fromString(authentication.getName());
        return ResponseEntity.ok(referralService.getOrCreateMyReferralCode(userId));
    }

    @GetMapping("/my-code")
    public ResponseEntity<ReferralCodeResponse> getMyReferralCode(
            Authentication authentication
    ) {
        UUID userId = UUID.fromString(authentication.getName());
        return ResponseEntity.ok(referralService.getMyReferralCode(userId));
    }

    @PostMapping("/validate")
    public ResponseEntity<ValidateReferralCodeResponse> validateReferralCode(
            Authentication authentication,
            @Valid @RequestBody ValidateReferralCodeRequest request
    ) {
        UUID userId = authentication != null
                ? UUID.fromString(authentication.getName())
                : null;

        return ResponseEntity.ok(referralService.validateReferralCode(userId, request));
    }
}