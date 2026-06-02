package com.localbuddy.safety;

import com.localbuddy.booking.Booking;
import com.localbuddy.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(
        name = "booking_safety_checklists",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_booking_safety_checklists_booking_user",
                        columnNames = {"booking_id", "user_id"}
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
public class BookingSafetyChecklist {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_context", nullable = false, length = 40)
    private BookingSafetyRoleContext roleContext;

    @Column(name = "public_meeting_acknowledged", nullable = false)
    private boolean publicMeetingAcknowledged = false;

    @Column(name = "communication_guidelines_acknowledged", nullable = false)
    private boolean communicationGuidelinesAcknowledged = false;

    @Column(name = "personal_safety_acknowledged", nullable = false)
    private boolean personalSafetyAcknowledged = false;

    @Column(name = "reporting_guidelines_acknowledged", nullable = false)
    private boolean reportingGuidelinesAcknowledged = false;

    @Column(name = "completed", nullable = false)
    private boolean completed = false;

    @Column(name = "completed_at")
    private Instant completedAt;

    @Column(name = "ip_address", length = 120)
    private String ipAddress;

    @Column(name = "user_agent", columnDefinition = "TEXT")
    private String userAgent;

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
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now();
    }
}