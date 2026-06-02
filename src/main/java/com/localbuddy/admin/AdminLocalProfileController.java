package com.localbuddy.admin;

import com.localbuddy.localprofile.LocalProfileResponse;
import com.localbuddy.localprofile.LocalProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/local-profiles")
public class AdminLocalProfileController {

    private final LocalProfileService localProfileService;

    public AdminLocalProfileController(LocalProfileService localProfileService) {
        this.localProfileService = localProfileService;
    }

    @GetMapping("/pending")
    public ResponseEntity<List<LocalProfileResponse>> getPendingLocalProfiles() {
        return ResponseEntity.ok(localProfileService.getPendingLocalProfiles());
    }

    @PostMapping("/{profileId}/approve")
    public ResponseEntity<LocalProfileResponse> approveLocalProfile(@PathVariable UUID profileId) {
        return ResponseEntity.ok(localProfileService.approveLocalProfile(profileId));
    }

    @PostMapping("/{profileId}/reject")
    public ResponseEntity<LocalProfileResponse> rejectLocalProfile(@PathVariable UUID profileId) {
        return ResponseEntity.ok(localProfileService.rejectLocalProfile(profileId));
    }
}