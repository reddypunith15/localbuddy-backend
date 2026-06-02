package com.localbuddy.safety;

import jakarta.validation.constraints.Size;

public record AdminSafetyReportDecisionRequest(

        @Size(max = 2000, message = "Admin notes cannot exceed 2000 characters")
        String adminNotes,

        @Size(max = 2000, message = "Resolution note cannot exceed 2000 characters")
        String resolutionNote
) {
}