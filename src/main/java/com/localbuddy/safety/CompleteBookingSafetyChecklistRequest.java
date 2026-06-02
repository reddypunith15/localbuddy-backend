package com.localbuddy.safety;

import jakarta.validation.constraints.AssertTrue;

public record CompleteBookingSafetyChecklistRequest(

        @AssertTrue(message = "Public meeting acknowledgement is required")
        Boolean publicMeetingAcknowledged,

        @AssertTrue(message = "Communication guidelines acknowledgement is required")
        Boolean communicationGuidelinesAcknowledged,

        @AssertTrue(message = "Personal safety acknowledgement is required")
        Boolean personalSafetyAcknowledged,

        @AssertTrue(message = "Reporting guidelines acknowledgement is required")
        Boolean reportingGuidelinesAcknowledged
) {
}