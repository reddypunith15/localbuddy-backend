package com.localbuddy.experience;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/experiences")
public class ExperienceController {

    private final ExperienceService experienceService;

    public ExperienceController(ExperienceService experienceService) {
        this.experienceService = experienceService;
    }

    @PostMapping
    public ResponseEntity<ExperienceResponse> createMyExperience(
            Authentication authentication,
            @Valid @RequestBody CreateExperienceRequest request
    ) {
        UUID userId = UUID.fromString(authentication.getName());
        ExperienceResponse response = experienceService.createMyExperience(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/me")
    public ResponseEntity<List<ExperienceResponse>> getMyExperiences(Authentication authentication) {
        UUID userId = UUID.fromString(authentication.getName());
        return ResponseEntity.ok(experienceService.getMyExperiences(userId));
    }

    @GetMapping("/me/{experienceId}")
    public ResponseEntity<ExperienceResponse> getMyExperienceById(
            Authentication authentication,
            @PathVariable UUID experienceId
    ) {
        UUID userId = UUID.fromString(authentication.getName());
        return ResponseEntity.ok(experienceService.getMyExperienceById(userId, experienceId));
    }

    @PutMapping("/{experienceId}")
    public ResponseEntity<ExperienceResponse> updateMyExperience(
            Authentication authentication,
            @PathVariable UUID experienceId,
            @Valid @RequestBody UpdateExperienceRequest request
    ) {
        UUID userId = UUID.fromString(authentication.getName());
        return ResponseEntity.ok(experienceService.updateMyExperience(userId, experienceId, request));
    }

    @PostMapping("/{experienceId}/submit")
    public ResponseEntity<ExperienceResponse> submitMyExperience(
            Authentication authentication,
            @PathVariable UUID experienceId
    ) {
        UUID userId = UUID.fromString(authentication.getName());
        return ResponseEntity.ok(experienceService.submitMyExperience(userId, experienceId));
    }
}