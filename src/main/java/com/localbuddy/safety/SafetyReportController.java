package com.localbuddy.safety;

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
@RequestMapping("/api/safety/reports")
@Tag(name = "Safety Reports", description = "Endpoints for filing and viewing safety reports")
@SecurityRequirement(name = "bearerAuth")
public class SafetyReportController {

    private final SafetyReportService safetyReportService;

    public SafetyReportController(SafetyReportService safetyReportService) {
        this.safetyReportService = safetyReportService;
    }

    @Operation(
            summary = "Create a safety report",
            description = "Allows the authenticated user to file a new safety report. The reporter is taken from the authenticated user."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Safety report created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body"),
            @ApiResponse(responseCode = "401", description = "Not authenticated")
    })
    @PostMapping
    public ResponseEntity<SafetyReportResponse> createReport(
            Authentication authentication,
            @Valid @RequestBody CreateSafetyReportRequest request
    ) {
        UUID userId = UUID.fromString(authentication.getName());
        SafetyReportResponse response = safetyReportService.createReport(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Get my safety reports",
            description = "Returns all safety reports filed by the currently authenticated user."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Safety reports retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Not authenticated")
    })
    @GetMapping("/me")
    public ResponseEntity<List<SafetyReportResponse>> getMyReports(Authentication authentication) {
        UUID userId = UUID.fromString(authentication.getName());
        return ResponseEntity.ok(safetyReportService.getMyReports(userId));
    }
}