package com.localbuddy.safety;

import com.localbuddy.booking.Booking;
import com.localbuddy.booking.BookingRepository;
import com.localbuddy.common.exception.BadRequestException;
import com.localbuddy.user.User;
import com.localbuddy.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class SafetyReportService {

    private final SafetyReportRepository safetyReportRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;

    public SafetyReportService(SafetyReportRepository safetyReportRepository,
                               UserRepository userRepository,
                               BookingRepository bookingRepository) {
        this.safetyReportRepository = safetyReportRepository;
        this.userRepository = userRepository;
        this.bookingRepository = bookingRepository;
    }

    @Transactional
    public SafetyReportResponse createReport(UUID reporterUserId, CreateSafetyReportRequest request) {
        User reporter = userRepository.findById(reporterUserId)
                .orElseThrow(() -> new BadRequestException("Invalid reporter"));

        User reportedUser = null;
        if (request.reportedUserId() != null) {
            reportedUser = userRepository.findById(request.reportedUserId())
                    .orElseThrow(() -> new BadRequestException("Invalid reported user"));

            if (reportedUser.getId().equals(reporter.getId())) {
                throw new BadRequestException("You cannot report yourself");
            }
        }

        Booking booking = null;
        if (request.bookingId() != null) {
            booking = bookingRepository.findById(request.bookingId())
                    .orElseThrow(() -> new BadRequestException("Invalid booking"));
        }

        SafetyReport report = new SafetyReport();
        report.setReporterUser(reporter);
        report.setReportedUser(reportedUser);
        report.setBooking(booking);
        report.setReportType(request.reportType());
        report.setSeverity(request.severity());
        report.setStatus(SafetyReportStatus.OPEN);
        report.setDescription(request.description().trim());

        return toResponse(safetyReportRepository.save(report));
    }

    @Transactional(readOnly = true)
    public List<SafetyReportResponse> getMyReports(UUID reporterUserId) {
        return safetyReportRepository.findByReporterUserIdOrderByCreatedAtDesc(reporterUserId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private SafetyReportResponse toResponse(SafetyReport report) {
        return new SafetyReportResponse(
                report.getId(),
                report.getReporterUser().getId(),
                report.getReportedUser() != null ? report.getReportedUser().getId() : null,
                report.getBooking() != null ? report.getBooking().getId() : null,
                report.getReportType(),
                report.getSeverity(),
                report.getStatus(),
                report.getDescription(),
                report.getAdminNotes(),
                report.getResolutionNote(),
                report.getCreatedAt(),
                report.getUpdatedAt(),
                report.getResolvedAt()
        );
    }

    @Transactional(readOnly = true)
    public List<SafetyReportResponse> getAdminReports(SafetyReportStatus status) {
        if (status != null) {
            return safetyReportRepository.findByStatusOrderByCreatedAtDesc(status)
                    .stream()
                    .map(this::toResponse)
                    .toList();
        }

        return safetyReportRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public SafetyReportResponse markReportInReview(UUID reportId, AdminSafetyReportDecisionRequest request) {
        SafetyReport report = safetyReportRepository.findById(reportId)
                .orElseThrow(() -> new BadRequestException("Safety report not found"));

        if (report.getStatus() == SafetyReportStatus.RESOLVED ||
                report.getStatus() == SafetyReportStatus.DISMISSED) {
            throw new BadRequestException("Closed report cannot be moved to review");
        }

        report.setStatus(SafetyReportStatus.IN_REVIEW);
        report.setAdminNotes(optionalTrim(request.adminNotes()));

        return toResponse(safetyReportRepository.save(report));
    }

    @Transactional
    public SafetyReportResponse resolveReport(UUID reportId, AdminSafetyReportDecisionRequest request) {
        SafetyReport report = safetyReportRepository.findById(reportId)
                .orElseThrow(() -> new BadRequestException("Safety report not found"));

        if (report.getStatus() == SafetyReportStatus.RESOLVED) {
            throw new BadRequestException("Report is already resolved");
        }

        report.setStatus(SafetyReportStatus.RESOLVED);
        report.setAdminNotes(optionalTrim(request.adminNotes()));
        report.setResolutionNote(optionalTrim(request.resolutionNote()));
        report.setResolvedAt(java.time.Instant.now());

        return toResponse(safetyReportRepository.save(report));
    }

    @Transactional
    public SafetyReportResponse dismissReport(UUID reportId, AdminSafetyReportDecisionRequest request) {
        SafetyReport report = safetyReportRepository.findById(reportId)
                .orElseThrow(() -> new BadRequestException("Safety report not found"));

        if (report.getStatus() == SafetyReportStatus.DISMISSED) {
            throw new BadRequestException("Report is already dismissed");
        }

        report.setStatus(SafetyReportStatus.DISMISSED);
        report.setAdminNotes(optionalTrim(request.adminNotes()));
        report.setResolutionNote(optionalTrim(request.resolutionNote()));
        report.setResolvedAt(java.time.Instant.now());

        return toResponse(safetyReportRepository.save(report));
    }

    private String optionalTrim(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        return value.trim();
    }
}