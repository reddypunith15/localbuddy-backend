package com.localbuddy.booking;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/cancellation-refund-policies")
public class AdminCancellationRefundPolicyController {

    private final CancellationRefundPolicyService policyService;

    public AdminCancellationRefundPolicyController(CancellationRefundPolicyService policyService) {
        this.policyService = policyService;
    }

    @GetMapping
    public ResponseEntity<List<CancellationRefundPolicyResponse>> getPolicies() {
        return ResponseEntity.ok(policyService.getPolicies());
    }

    @PostMapping
    public ResponseEntity<CancellationRefundPolicyResponse> createPolicy(
            @Valid @RequestBody UpsertCancellationRefundPolicyRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(policyService.createPolicy(request));
    }

    @PutMapping("/{policyId}")
    public ResponseEntity<CancellationRefundPolicyResponse> updatePolicy(
            @PathVariable UUID policyId,
            @Valid @RequestBody UpsertCancellationRefundPolicyRequest request
    ) {
        return ResponseEntity.ok(policyService.updatePolicy(policyId, request));
    }

    @PostMapping("/{policyId}/deactivate")
    public ResponseEntity<CancellationRefundPolicyResponse> deactivatePolicy(
            @PathVariable UUID policyId
    ) {
        return ResponseEntity.ok(policyService.deactivatePolicy(policyId));
    }
}