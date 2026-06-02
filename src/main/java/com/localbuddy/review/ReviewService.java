package com.localbuddy.review;

import com.localbuddy.booking.Booking;
import com.localbuddy.booking.BookingRepository;
import com.localbuddy.booking.BookingStatus;
import com.localbuddy.common.exception.BadRequestException;
import com.localbuddy.common.exception.ResourceNotFoundException;
import com.localbuddy.user.User;
import com.localbuddy.user.UserRepository;
import com.localbuddy.user.UserRole;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;

    public ReviewService(ReviewRepository reviewRepository,
                         BookingRepository bookingRepository,
                         UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public ReviewResponse createReview(UUID reviewerUserId, CreateReviewRequest request) {
        User reviewer = userRepository.findById(reviewerUserId)
                .orElseThrow(() -> new BadRequestException("Invalid user"));

        if (reviewer.getRole() != UserRole.TRAVELER) {
            throw new BadRequestException("Only travelers can create reviews");
        }

        Booking booking = bookingRepository.findById(request.bookingId())
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        if (booking.getTravelerUser() == null ||
                !booking.getTravelerUser().getId().equals(reviewerUserId)) {
            throw new ResourceNotFoundException("Booking not found");
        }

        if (reviewRepository.existsByBookingId(booking.getId())) {
            throw new BadRequestException("Review already exists for this booking");
        }

        if (booking.getStatus() != BookingStatus.COMPLETED) {
            throw new BadRequestException("Review is allowed only after the booking is completed");
        }

        Review review = new Review();
        review.setBooking(booking);
        review.setReviewerUser(reviewer);
        review.setLocalProfile(booking.getLocalProfile());
        review.setExperience(booking.getExperience());
        review.setRating(request.rating());
        review.setComment(optionalTrim(request.comment()));
        review.setStatus(ReviewStatus.VISIBLE);

        return toResponse(reviewRepository.save(review));
    }

    @Transactional(readOnly = true)
    public List<ReviewResponse> getMyReviews(UUID reviewerUserId) {
        return reviewRepository.findByReviewerUserIdOrderByCreatedAtDesc(reviewerUserId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ReviewResponse> getPublicReviewsForLocalProfile(UUID localProfileId) {
        return reviewRepository.findByLocalProfileIdAndStatusOrderByCreatedAtDesc(
                        localProfileId,
                        ReviewStatus.VISIBLE
                )
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ReviewResponse> getPublicReviewsForExperience(UUID experienceId) {
        return reviewRepository.findByExperienceIdAndStatusOrderByCreatedAtDesc(
                        experienceId,
                        ReviewStatus.VISIBLE
                )
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public ReviewResponse hideReview(UUID reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found"));

        review.setStatus(ReviewStatus.HIDDEN);

        return toResponse(reviewRepository.save(review));
    }

    @Transactional
    public ReviewResponse unhideReview(UUID reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found"));

        review.setStatus(ReviewStatus.VISIBLE);

        return toResponse(reviewRepository.save(review));
    }

    @Transactional(readOnly = true)
    public List<ReviewResponse> getAdminReviews() {
        return reviewRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private String optionalTrim(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        return value.trim();
    }

    private ReviewResponse toResponse(Review review) {
        return new ReviewResponse(
                review.getId(),
                review.getBooking().getId(),
                review.getReviewerUser() != null ? review.getReviewerUser().getId() : null,
                review.getLocalProfile().getId(),
                review.getExperience().getId(),
                review.getRating(),
                review.getComment(),
                review.getStatus(),
                review.getCreatedAt(),
                review.getUpdatedAt()
        );
    }
}