package com.localbuddy.safety;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/safety/reports")
public class SafetyReportController {

    private final SafetyReportService safetyReportService;

    public SafetyReportController(SafetyReportService safetyReportService) {
        this.safetyReportService = safetyReportService;
    }

    @PostMapping
    public ResponseEntity<SafetyReportResponse> createReport(
            Authentication authentication,
            @Valid @RequestBody CreateSafetyReportRequest request
    ) {
        UUID userId = UUID.fromString(authentication.getName());
        SafetyReportResponse response = safetyReportService.createReport(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/me")
    public ResponseEntity<List<SafetyReportResponse>> getMyReports(Authentication authentication) {
        UUID userId = UUID.fromString(authentication.getName());
        return ResponseEntity.ok(safetyReportService.getMyReports(userId));
    }
}