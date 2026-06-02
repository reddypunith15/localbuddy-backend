package com.localbuddy.localprofile;

import com.localbuddy.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "local_profiles")
@Getter
@Setter
@NoArgsConstructor
public class LocalProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(name = "display_name", nullable = false, length = 150)
    private String displayName;

    @Column(name = "bio", columnDefinition = "TEXT")
    private String bio;

    @Column(name = "city", nullable = false, length = 100)
    private String city;

    @Column(name = "country", nullable = false, length = 100)
    private String country;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "languages", columnDefinition = "jsonb")
    private List<String> languages = List.of();

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "interests", columnDefinition = "jsonb")
    private List<String> interests = List.of();

    @Column(name = "occupation", length = 150)
    private String occupation;

    @Column(name = "profile_photo_url", columnDefinition = "TEXT")
    private String profilePhotoUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "verification_status", nullable = false, length = 40)
    private LocalVerificationStatus verificationStatus = LocalVerificationStatus.NOT_STARTED;

    @Enumerated(EnumType.STRING)
    @Column(name = "approval_status", nullable = false, length = 40)
    private LocalApprovalStatus approvalStatus = LocalApprovalStatus.DRAFT;

    @Column(name = "admin_review_note", columnDefinition = "TEXT")
    private String adminReviewNote;

    @Column(name = "rejection_reason", columnDefinition = "TEXT")
    private String rejectionReason;

    @Column(name = "changes_requested_reason", columnDefinition = "TEXT")
    private String changesRequestedReason;

    @Column(name = "reviewed_at")
    private Instant reviewedAt;

    @Column(name = "submitted_at")
    private Instant submittedAt;

    @Column(name = "resubmitted_at")
    private Instant resubmittedAt;

    @Column(name = "legal_first_name", length = 120)
    private String legalFirstName;

    @Column(name = "legal_last_name", length = 120)
    private String legalLastName;

    @Column(name = "preferred_name", length = 120)
    private String preferredName;

    @Column(name = "current_city", length = 120)
    private String currentCity;

    @Column(name = "current_address", columnDefinition = "TEXT")
    private String currentAddress;

    @Column(name = "buddy_city", length = 120)
    private String buddyCity;

    @Column(name = "verification_provider", length = 80)
    private String verificationProvider;

    @Column(name = "verification_reference_id")
    private String verificationReferenceId;

    @Column(name = "verification_started_at")
    private Instant verificationStartedAt;

    @Column(name = "verification_completed_at")
    private Instant verificationCompletedAt;

    @Column(name = "verification_failure_reason", columnDefinition = "TEXT")
    private String verificationFailureReason;

    @Column(name = "rating_avg", nullable = false, precision = 3, scale = 2)
    private BigDecimal ratingAvg = BigDecimal.ZERO;

    @Column(name = "total_reviews", nullable = false)
    private Integer totalReviews = 0;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @PrePersist
    protected void onCreate() {
        Instant now = Instant.now();

        if (createdAt == null) {
            createdAt = now;
        }

        if (updatedAt == null) {
            updatedAt = now;
        }

        if (verificationStatus == null) {
            verificationStatus = LocalVerificationStatus.NOT_STARTED;
        }

        if (approvalStatus == null) {
            approvalStatus = LocalApprovalStatus.DRAFT;
        }

        if (ratingAvg == null) {
            ratingAvg = BigDecimal.ZERO;
        }

        if (totalReviews == null) {
            totalReviews = 0;
        }

        if (languages == null) {
            languages = List.of();
        }

        if (interests == null) {
            interests = List.of();
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now();
    }
}