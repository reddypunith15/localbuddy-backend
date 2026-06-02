package com.localbuddy.trustsafety;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/trust-safety")
public class TrustSafetyController {

    private final TrustSafetyService trustSafetyService;

    public TrustSafetyController(TrustSafetyService trustSafetyService) {
        this.trustSafetyService = trustSafetyService;
    }

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