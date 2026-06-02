package com.localbuddy.localprofile;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/local-profiles")
public class LocalProfileController {

    private final LocalProfileService localProfileService;

    public LocalProfileController(LocalProfileService localProfileService) {
        this.localProfileService = localProfileService;
    }

    @PostMapping("/me")
    public ResponseEntity<LocalProfileResponse> createMyLocalProfile(
            Authentication authentication,
            @Valid @RequestBody CreateLocalProfileRequest request
    ) {
        UUID userId = UUID.fromString(authentication.getName());
        LocalProfileResponse response = localProfileService.createMyLocalProfile(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/me")
    public ResponseEntity<LocalProfileResponse> getMyLocalProfile(Authentication authentication) {
        UUID userId = UUID.fromString(authentication.getName());
        return ResponseEntity.ok(localProfileService.getMyLocalProfile(userId));
    }

    @PutMapping("/me")
    public ResponseEntity<LocalProfileResponse> updateMyLocalProfile(
            Authentication authentication,
            @Valid @RequestBody UpdateLocalProfileRequest request
    ) {
        UUID userId = UUID.fromString(authentication.getName());
        return ResponseEntity.ok(localProfileService.updateMyLocalProfile(userId, request));
    }

    @PostMapping("/me/submit")
    public ResponseEntity<LocalProfileResponse> submitMyLocalProfile(Authentication authentication) {
        UUID userId = UUID.fromString(authentication.getName());
        return ResponseEntity.ok(localProfileService.submitMyLocalProfile(userId));
    }

    @PostMapping("/admin/local-profiles/{profileId}/request-changes")
    public ResponseEntity<LocalProfileResponse> requestChangesForLocalProfile(
            @PathVariable UUID profileId,
            @Valid @RequestBody AdminLocalProfileReviewRequest request
    ) {
        return ResponseEntity.ok(
                localProfileService.requestChangesForLocalProfile(profileId, request)
        );
    }

    @GetMapping("/onboarding-status")
    public ResponseEntity<LocalOnboardingStatusResponse> getMyOnboardingStatus(
            Authentication authentication
    ) {
        UUID userId = UUID.fromString(authentication.getName());
        return ResponseEntity.ok(localProfileService.getMyOnboardingStatus(userId));
    }
}