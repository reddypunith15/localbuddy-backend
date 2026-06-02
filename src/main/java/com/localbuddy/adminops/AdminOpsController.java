package com.localbuddy.adminops;

import com.localbuddy.experience.ExperienceResponse;
import com.localbuddy.localprofile.LocalProfileResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/ops")
public class AdminOpsController {

    private final AdminOpsService adminOpsService;

    public AdminOpsController(AdminOpsService adminOpsService) {
        this.adminOpsService = adminOpsService;
    }

    @PostMapping("/local-profiles")
    public ResponseEntity<LocalProfileResponse> createOrApproveLocalProfile(
            @Valid @RequestBody AdminCreateLocalProfileRequest request
    ) {
        LocalProfileResponse response = adminOpsService.createOrApproveLocalProfile(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/experiences")
    public ResponseEntity<ExperienceResponse> createApprovedExperience(
            @Valid @RequestBody AdminCreateExperienceRequest request
    ) {
        ExperienceResponse response = adminOpsService.createApprovedExperience(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}