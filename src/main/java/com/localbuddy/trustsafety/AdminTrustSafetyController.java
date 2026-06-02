package com.localbuddy.trustsafety;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/trust-safety")
public class AdminTrustSafetyController {

    private final TrustSafetyService trustSafetyService;

    public AdminTrustSafetyController(TrustSafetyService trustSafetyService) {
        this.trustSafetyService = trustSafetyService;
    }

    @GetMapping("/reports")
    public ResponseEntity<List<SafetyReportResponse>> getReports(
            @RequestParam(required = false) SafetyReportStatus status
    ) {
        return ResponseEntity.ok(trustSafetyService.getAdminReports(status));
    }

    @PutMapping("/reports/{reportId}")
    public ResponseEntity<SafetyReportResponse> updateReport(
            @PathVariable UUID reportId,
            @Valid @RequestBody UpdateSafetyReportRequest request
    ) {
        return ResponseEntity.ok(trustSafetyService.updateReport(reportId, request));
    }

    @GetMapping("/restrictions")
    public ResponseEntity<List<UserRestrictionResponse>> getActiveRestrictions() {
        return ResponseEntity.ok(trustSafetyService.getActiveRestrictions());
    }

    @PostMapping("/restrictions")
    public ResponseEntity<UserRestrictionResponse> createRestriction(
            Authentication authentication,
            @Valid @RequestBody CreateUserRestrictionRequest request
    ) {
        UUID adminUserId = UUID.fromString(authentication.getName());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(trustSafetyService.createRestriction(adminUserId, request));
    }

    @PostMapping("/restrictions/{restrictionId}/deactivate")
    public ResponseEntity<UserRestrictionResponse> deactivateRestriction(
            @PathVariable UUID restrictionId
    ) {
        return ResponseEntity.ok(trustSafetyService.deactivateRestriction(restrictionId));
    }
}