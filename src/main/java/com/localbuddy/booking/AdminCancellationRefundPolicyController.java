package com.localbuddy.booking;

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
@RequestMapping("/api/admin/cancellation-refund-policies")
@Tag(name = "Admin - Cancellation & Refund Policies", description = "Admin endpoints for managing cancellation and refund policies")
@SecurityRequirement(name = "bearerAuth")
public class AdminCancellationRefundPolicyController {

    private final CancellationRefundPolicyService policyService;

    public AdminCancellationRefundPolicyController(CancellationRefundPolicyService policyService) {
        this.policyService = policyService;
    }

    @Operation(
            summary = "Get all cancellation/refund policies",
            description = "Returns the list of all cancellation and refund policies. Admin only."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Policies retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Not authenticated"),
            @ApiResponse(responseCode = "403", description = "Not authorized (admin only)")
    })
    @GetMapping
    public ResponseEntity<List<CancellationRefundPolicyResponse>> getPolicies() {
        return ResponseEntity.ok(policyService.getPolicies());
    }

    @Operation(
            summary = "Create a cancellation/refund policy",
            description = "Creates a new cancellation and refund policy. Admin only."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Policy created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body"),
            @ApiResponse(responseCode = "401", description = "Not authenticated"),
            @ApiResponse(responseCode = "403", description = "Not authorized (admin only)")
    })
    @PostMapping
    public ResponseEntity<CancellationRefundPolicyResponse> createPolicy(
            @Valid @RequestBody UpsertCancellationRefundPolicyRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(policyService.createPolicy(request));
    }

    @Operation(
            summary = "Update a cancellation/refund policy",
            description = "Updates an existing cancellation and refund policy by its ID. Admin only."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Policy updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body"),
            @ApiResponse(responseCode = "401", description = "Not authenticated"),
            @ApiResponse(responseCode = "403", description = "Not authorized (admin only)"),
            @ApiResponse(responseCode = "404", description = "Policy not found")
    })
    @PutMapping("/{policyId}")
    public ResponseEntity<CancellationRefundPolicyResponse> updatePolicy(
            @PathVariable UUID policyId,
            @Valid @RequestBody UpsertCancellationRefundPolicyRequest request
    ) {
        return ResponseEntity.ok(policyService.updatePolicy(policyId, request));
    }

    @Operation(
            summary = "Deactivate a cancellation/refund policy",
            description = "Deactivates an existing cancellation and refund policy by its ID. Admin only."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Policy deactivated successfully"),
            @ApiResponse(responseCode = "401", description = "Not authenticated"),
            @ApiResponse(responseCode = "403", description = "Not authorized (admin only)"),
            @ApiResponse(responseCode = "404", description = "Policy not found")
    })
    @PostMapping("/{policyId}/deactivate")
    public ResponseEntity<CancellationRefundPolicyResponse> deactivatePolicy(
            @PathVariable UUID policyId
    ) {
        return ResponseEntity.ok(policyService.deactivatePolicy(policyId));
    }
}