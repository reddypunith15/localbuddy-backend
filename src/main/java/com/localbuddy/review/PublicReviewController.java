package com.localbuddy.review;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/public")
public class PublicReviewController {

    private final ReviewService reviewService;

    public PublicReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/local-profiles/{localProfileId}/reviews")
    public ResponseEntity<List<ReviewResponse>> getReviewsForLocalProfile(
            @PathVariable UUID localProfileId
    ) {
        return ResponseEntity.ok(reviewService.getPublicReviewsForLocalProfile(localProfileId));
    }

    @GetMapping("/experiences/{experienceId}/reviews")
    public ResponseEntity<List<ReviewResponse>> getReviewsForExperience(
            @PathVariable UUID experienceId
    ) {
        return ResponseEntity.ok(reviewService.getPublicReviewsForExperience(experienceId));
    }
}