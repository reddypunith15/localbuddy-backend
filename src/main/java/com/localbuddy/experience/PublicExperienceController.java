package com.localbuddy.experience;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/public/experiences")
public class PublicExperienceController {

    private final ExperienceService experienceService;

    public PublicExperienceController(ExperienceService experienceService) {
        this.experienceService = experienceService;
    }

    @GetMapping
    public ResponseEntity<List<ExperienceResponse>> getApprovedExperiences(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String categorySlug
    ) {
        return ResponseEntity.ok(experienceService.getApprovedExperiences(city, categorySlug));
    }

    @GetMapping("/{experienceId}")
    public ResponseEntity<ExperienceResponse> getApprovedExperienceById(
            @PathVariable UUID experienceId
    ) {
        return ResponseEntity.ok(experienceService.getApprovedExperienceById(experienceId));
    }

    @GetMapping("/slug/{slug}")
    public ResponseEntity<ExperienceResponse> getApprovedExperienceBySlug(
            @PathVariable String slug
    ) {
        return ResponseEntity.ok(experienceService.getApprovedExperienceBySlug(slug));
    }
}