package com.localbuddy.availability;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/public/experiences")
public class PublicAvailabilityController {

    private final AvailabilitySlotService availabilitySlotService;

    public PublicAvailabilityController(AvailabilitySlotService availabilitySlotService) {
        this.availabilitySlotService = availabilitySlotService;
    }

    @GetMapping("/{experienceId}/availability")
    public ResponseEntity<List<AvailabilitySlotResponse>> getPublicAvailabilityForExperience(
            @PathVariable UUID experienceId
    ) {
        return ResponseEntity.ok(
                availabilitySlotService.getPublicAvailabilityForExperience(experienceId)
        );
    }
}