package com.localbuddy.trustsafety;

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

import java.util.UUID;

@RestController
@RequestMapping("/api/trust-safety")
@Tag(name = "Trust & Safety", description = "Endpoints for safety reports and trust & safety actions")
@SecurityRequirement(name = "bearerAuth")
public class TrustSafetyController {

    private final TrustSafetyService trustSafetyService;

    public TrustSafetyController(TrustSafetyService trustSafetyService) {
        this.trustSafetyService = trustSafetyService;
    }

    @Operation(
            summary = "Create a safety report",
            description = "Allows the authenticated user to file a safety report. The reporter is taken from the authenticated user."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Safety report created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body"),
            @ApiResponse(responseCode = "401", description = "Not authenticated")
    })
    @PostMapping("/reports")
    public ResponseEntity<SafetyReportResponse> createSafetyReport(
            Authentication authentication,
            @Valid @RequestBody CreateSafetyReportRequest request
    ) {
        UUID reporterUserId = UUID.fromString(authentication.getName());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(trustSafetyService.createSafetyReport(reporterUserId, request));
    }
}