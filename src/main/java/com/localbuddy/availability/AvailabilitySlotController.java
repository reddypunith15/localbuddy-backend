package com.localbuddy.availability;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/availability")
public class AvailabilitySlotController {

    private final AvailabilitySlotService availabilitySlotService;

    public AvailabilitySlotController(AvailabilitySlotService availabilitySlotService) {
        this.availabilitySlotService = availabilitySlotService;
    }

    @PostMapping
    public ResponseEntity<AvailabilitySlotResponse> createMyAvailabilitySlot(
            Authentication authentication,
            @Valid @RequestBody CreateAvailabilitySlotRequest request
    ) {
        UUID userId = UUID.fromString(authentication.getName());
        AvailabilitySlotResponse response = availabilitySlotService.createMyAvailabilitySlot(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/me")
    public ResponseEntity<List<AvailabilitySlotResponse>> getMyAvailabilitySlots(Authentication authentication) {
        UUID userId = UUID.fromString(authentication.getName());
        return ResponseEntity.ok(availabilitySlotService.getMyAvailabilitySlots(userId));
    }
}