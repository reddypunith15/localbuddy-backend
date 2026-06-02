package com.localbuddy.admin;

import com.localbuddy.safety.AdminSafetyReportDecisionRequest;
import com.localbuddy.safety.SafetyReportResponse;
import com.localbuddy.safety.SafetyReportService;
import com.localbuddy.safety.SafetyReportStatus;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/safety/reports")
public class AdminSafetyReportController {

    private final SafetyReportService safetyReportService;

    public AdminSafetyReportController(SafetyReportService safetyReportService) {
        this.safetyReportService = safetyReportService;
    }

    @GetMapping
    public ResponseEntity<List<SafetyReportResponse>> getAdminReports(
            @RequestParam(required = false) SafetyReportStatus status
    ) {
        return ResponseEntity.ok(safetyReportService.getAdminReports(status));
    }

    @PostMapping("/{reportId}/mark-in-review")
    public ResponseEntity<SafetyReportResponse> markReportInReview(
            @PathVariable UUID reportId,
            @Valid @RequestBody AdminSafetyReportDecisionRequest request
    ) {
        return ResponseEntity.ok(safetyReportService.markReportInReview(reportId, request));
    }

    @PostMapping("/{reportId}/resolve")
    public ResponseEntity<SafetyReportResponse> resolveReport(
            @PathVariable UUID reportId,
            @Valid @RequestBody AdminSafetyReportDecisionRequest request
    ) {
        return ResponseEntity.ok(safetyReportService.resolveReport(reportId, request));
    }

    @PostMapping("/{reportId}/dismiss")
    public ResponseEntity<SafetyReportResponse> dismissReport(
            @PathVariable UUID reportId,
            @Valid @RequestBody AdminSafetyReportDecisionRequest request
    ) {
        return ResponseEntity.ok(safetyReportService.dismissReport(reportId, request));
    }
}