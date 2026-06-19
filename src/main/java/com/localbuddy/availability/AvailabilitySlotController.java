package com.localbuddy.availability;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/availability")
@Tag(name = "Availability", description = "Endpoints for managing a local's availability slots")
@SecurityRequirement(name = "bearerAuth")
public class AvailabilitySlotController {

    private final AvailabilitySlotService availabilitySlotService;

    public AvailabilitySlotController(AvailabilitySlotService availabilitySlotService) {
        this.availabilitySlotService = availabilitySlotService;
    }

    @Operation(
            summary = "Create my availability slot",
            description = "Creates a new availability slot for the currently authenticated user (local)."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Availability slot created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body"),
            @ApiResponse(responseCode = "401", description = "Not authenticated")
    })
    @PostMapping
    public ResponseEntity<AvailabilitySlotResponse> createMyAvailabilitySlot(
            Authentication authentication,
            @Valid @RequestBody CreateAvailabilitySlotRequest request
    ) {
        UUID userId = UUID.fromString(authentication.getName());
        AvailabilitySlotResponse response = availabilitySlotService.createMyAvailabilitySlot(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Get my availability slots",
            description = "Returns all availability slots belonging to the currently authenticated user (local)."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Availability slots retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Not authenticated")
    })
    @GetMapping("/me")
    public ResponseEntity<List<AvailabilitySlotResponse>> getMyAvailabilitySlots(Authentication authentication) {
        UUID userId = UUID.fromString(authentication.getName());
        return ResponseEntity.ok(availabilitySlotService.getMyAvailabilitySlots(userId));
    }
}