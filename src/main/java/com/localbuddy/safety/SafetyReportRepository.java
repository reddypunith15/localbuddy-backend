package com.localbuddy.safety;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SafetyReportRepository extends JpaRepository<SafetyReport, UUID> {

    List<SafetyReport> findByReporterUserIdOrderByCreatedAtDesc(UUID reporterUserId);

    List<SafetyReport> findByReportedUserIdOrderByCreatedAtDesc(UUID reportedUserId);

    List<SafetyReport> findByBookingIdOrderByCreatedAtDesc(UUID bookingId);

    List<SafetyReport> findByStatusOrderByCreatedAtDesc(SafetyReportStatus status);

    List<SafetyReport> findBySeverityOrderByCreatedAtDesc(SafetySeverity severity);

    List<SafetyReport> findAllByOrderByCreatedAtDesc();

    long countByStatus(SafetyReportStatus status);
}