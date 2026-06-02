package com.localbuddy.admin;

import com.localbuddy.review.AdminReviewModerationRequest;
import com.localbuddy.review.ReviewResponse;
import com.localbuddy.review.ReviewService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/reviews")
public class AdminReviewController {

    private final ReviewService reviewService;

    public AdminReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public ResponseEntity<List<ReviewResponse>> getAdminReviews() {
        return ResponseEntity.ok(reviewService.getAdminReviews());
    }

    @PostMapping("/{reviewId}/hide")
    public ResponseEntity<ReviewResponse> hideReview(
            @PathVariable UUID reviewId,
            @Valid @RequestBody AdminReviewModerationRequest request
    ) {
        return ResponseEntity.ok(reviewService.hideReview(reviewId));
    }

    @PostMapping("/{reviewId}/unhide")
    public ResponseEntity<ReviewResponse> unhideReview(
            @PathVariable UUID reviewId,
            @Valid @RequestBody AdminReviewModerationRequest request
    ) {
        return ResponseEntity.ok(reviewService.unhideReview(reviewId));
    }
}