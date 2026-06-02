package com.localbuddy.admin;

import com.localbuddy.experience.ExperienceResponse;
import com.localbuddy.experience.ExperienceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/experiences")
public class AdminExperienceController {

    private final ExperienceService experienceService;

    public AdminExperienceController(ExperienceService experienceService) {
        this.experienceService = experienceService;
    }

    @GetMapping("/pending")
    public ResponseEntity<List<ExperienceResponse>> getPendingExperiences() {
        return ResponseEntity.ok(experienceService.getPendingExperiences());
    }

    @PostMapping("/{experienceId}/approve")
    public ResponseEntity<ExperienceResponse> approveExperience(@PathVariable UUID experienceId) {
        return ResponseEntity.ok(experienceService.approveExperience(experienceId));
    }

    @PostMapping("/{experienceId}/reject")
    public ResponseEntity<ExperienceResponse> rejectExperience(@PathVariable UUID experienceId) {
        return ResponseEntity.ok(experienceService.rejectExperience(experienceId));
    }
}