package com.localbuddy.admin;

import com.localbuddy.experience.CityResponse;
import com.localbuddy.experience.CityService;
import com.localbuddy.experience.CreateCityRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/cities")
@Tag(name = "Admin - Cities", description = "Admin endpoints for managing the pool of cities where experiences can be offered")
@SecurityRequirement(name = "bearerAuth")
public class AdminCityController {

    private final CityService cityService;

    public AdminCityController(CityService cityService) {
        this.cityService = cityService;
    }

    @Operation(
            summary = "List all cities",
            description = "Returns all cities, including inactive ones. Admin only."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cities retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Not authenticated"),
            @ApiResponse(responseCode = "403", description = "Not authorized (admin only)")
    })
    @GetMapping
    public ResponseEntity<List<CityResponse>> getAllCities() {
        return ResponseEntity.ok(cityService.getAllCities());
    }

    @Operation(
            summary = "Add a city",
            description = "Adds a new city to the pool that hosts can offer experiences in. Admin only."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "City created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request or duplicate city name"),
            @ApiResponse(responseCode = "401", description = "Not authenticated"),
            @ApiResponse(responseCode = "403", description = "Not authorized (admin only)")
    })
    @PostMapping
    public ResponseEntity<CityResponse> createCity(@Valid @RequestBody CreateCityRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cityService.createCity(request));
    }

    @Operation(
            summary = "Activate a city",
            description = "Makes a city selectable for new experiences. Admin only."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "City activated successfully"),
            @ApiResponse(responseCode = "401", description = "Not authenticated"),
            @ApiResponse(responseCode = "403", description = "Not authorized (admin only)"),
            @ApiResponse(responseCode = "404", description = "City not found")
    })
    @PostMapping("/{cityId}/activate")
    public ResponseEntity<CityResponse> activateCity(@PathVariable UUID cityId) {
        return ResponseEntity.ok(cityService.setCityActive(cityId, true));
    }

    @Operation(
            summary = "Deactivate a city",
            description = "Removes a city from the selectable pool for new experiences. Existing experiences are unaffected. Admin only."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "City deactivated successfully"),
            @ApiResponse(responseCode = "401", description = "Not authenticated"),
            @ApiResponse(responseCode = "403", description = "Not authorized (admin only)"),
            @ApiResponse(responseCode = "404", description = "City not found")
    })
    @PostMapping("/{cityId}/deactivate")
    public ResponseEntity<CityResponse> deactivateCity(@PathVariable UUID cityId) {
        return ResponseEntity.ok(cityService.setCityActive(cityId, false));
    }
}
