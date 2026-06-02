package com.localbuddy.localprofile;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/public/local-profiles")
public class PublicLocalProfileController {

    private final LocalProfileService localProfileService;

    public PublicLocalProfileController(LocalProfileService localProfileService) {
        this.localProfileService = localProfileService;
    }

    @GetMapping
    public ResponseEntity<List<LocalProfileResponse>> getApprovedLocalProfiles(
            @RequestParam(required = false) String city
    ) {
        return ResponseEntity.ok(localProfileService.getApprovedLocalProfiles(city));
    }

    @GetMapping("/{profileId}")
    public ResponseEntity<LocalProfileResponse> getApprovedLocalProfileById(
            @PathVariable UUID profileId
    ) {
        return ResponseEntity.ok(localProfileService.getApprovedLocalProfileById(profileId));
    }
}