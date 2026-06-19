package com.localbuddy.referral;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/referrals")
@Tag(name = "Referrals", description = "Endpoints for managing and validating referral codes")
@SecurityRequirement(name = "bearerAuth")
public class ReferralController {

    private final ReferralService referralService;

    public ReferralController(ReferralService referralService) {
        this.referralService = referralService;
    }

    @Operation(
            summary = "Get or create my referral code",
            description = "Returns the authenticated user's referral code, creating one if it doesn't exist yet."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Referral code retrieved or created successfully"),
            @ApiResponse(responseCode = "401", description = "Not authenticated")
    })
    @PostMapping("/my-code")
    public ResponseEntity<ReferralCodeResponse> getOrCreateMyReferralCode(
            Authentication authentication
    ) {
        UUID userId = UUID.fromString(authentication.getName());
        return ResponseEntity.ok(referralService.getOrCreateMyReferralCode(userId));
    }

    @Operation(
            summary = "Get my referral code",
            description = "Returns the authenticated user's existing referral code."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Referral code retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Not authenticated"),
            @ApiResponse(responseCode = "404", description = "Referral code not found")
    })
    @GetMapping("/my-code")
    public ResponseEntity<ReferralCodeResponse> getMyReferralCode(
            Authentication authentication
    ) {
        UUID userId = UUID.fromString(authentication.getName());
        return ResponseEntity.ok(referralService.getMyReferralCode(userId));
    }

    @Operation(
            summary = "Validate a referral code",
            description = "Validates a referral code. Works for both authenticated and anonymous users; if authenticated, the user is associated with the validation."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Referral code validation result returned"),
            @ApiResponse(responseCode = "400", description = "Invalid request body")
    })
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