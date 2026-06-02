package com.localbuddy.consent;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/consents")
public class ConsentController {

    private final ConsentService consentService;

    public ConsentController(ConsentService consentService) {
        this.consentService = consentService;
    }

    @GetMapping("/my-status")
    public ResponseEntity<ConsentStatusResponse> getMyConsentStatus(
            Authentication authentication
    ) {
        UUID userId = UUID.fromString(authentication.getName());
        return ResponseEntity.ok(consentService.getMyConsentStatus(userId));
    }

    @PostMapping("/accept")
    public ResponseEntity<UserConsentResponse> acceptConsent(
            Authentication authentication,
            HttpServletRequest servletRequest,
            @Valid @RequestBody AcceptConsentRequest request
    ) {
        UUID userId = UUID.fromString(authentication.getName());

        UserConsentResponse response = consentService.acceptConsent(
                userId,
                request,
                resolveClientIp(servletRequest),
                servletRequest.getHeader("User-Agent")
        );

        return ResponseEntity.ok(response);
    }

    private String resolveClientIp(HttpServletRequest request) {
        String forwardedFor = request.getHeader("X-Forwarded-For");

        if (forwardedFor != null && !forwardedFor.isBlank()) {
            return forwardedFor.split(",")[0].trim();
        }

        String realIp = request.getHeader("X-Real-IP");

        if (realIp != null && !realIp.isBlank()) {
            return realIp.trim();
        }

        return request.getRemoteAddr();
    }

    @PostMapping("/accept-required-traveler")
    public ResponseEntity<List<UserConsentResponse>> acceptRequiredTravelerConsents(
            Authentication authentication,
            HttpServletRequest servletRequest
    ) {
        UUID userId = UUID.fromString(authentication.getName());

        List<UserConsentResponse> response = consentService.acceptRequiredTravelerConsents(
                userId,
                resolveClientIp(servletRequest),
                servletRequest.getHeader("User-Agent")
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping("/accept-required-local")
    public ResponseEntity<List<UserConsentResponse>> acceptRequiredLocalConsents(
            Authentication authentication,
            HttpServletRequest servletRequest
    ) {
        UUID userId = UUID.fromString(authentication.getName());

        List<UserConsentResponse> response = consentService.acceptRequiredLocalConsents(
                userId,
                resolveClientIp(servletRequest),
                servletRequest.getHeader("User-Agent")
        );

        return ResponseEntity.ok(response);
    }
}