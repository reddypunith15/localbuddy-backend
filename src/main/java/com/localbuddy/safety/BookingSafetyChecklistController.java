package com.localbuddy.safety;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/bookings/{bookingId}/safety-checklist")
public class BookingSafetyChecklistController {

    private final BookingSafetyChecklistService checklistService;

    public BookingSafetyChecklistController(BookingSafetyChecklistService checklistService) {
        this.checklistService = checklistService;
    }

    @GetMapping
    public ResponseEntity<BookingSafetyChecklistResponse> getMyChecklist(
            Authentication authentication,
            @PathVariable UUID bookingId
    ) {
        UUID userId = UUID.fromString(authentication.getName());
        return ResponseEntity.ok(checklistService.getMyChecklist(userId, bookingId));
    }

    @PostMapping("/complete")
    public ResponseEntity<BookingSafetyChecklistResponse> completeMyChecklist(
            Authentication authentication,
            @PathVariable UUID bookingId,
            HttpServletRequest servletRequest,
            @Valid @RequestBody CompleteBookingSafetyChecklistRequest request
    ) {
        UUID userId = UUID.fromString(authentication.getName());

        return ResponseEntity.ok(
                checklistService.completeMyChecklist(
                        userId,
                        bookingId,
                        request,
                        resolveClientIp(servletRequest),
                        servletRequest.getHeader("User-Agent")
                )
        );
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
}