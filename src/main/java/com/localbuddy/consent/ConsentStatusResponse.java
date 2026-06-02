package com.localbuddy.consent;

import java.util.List;

public record ConsentStatusResponse(
        boolean hasAcceptedRequiredTravelerConsents,
        boolean hasAcceptedRequiredLocalConsents,
        String currentVersion,
        List<ConsentType> acceptedConsentTypes,
        List<ConsentType> missingTravelerConsentTypes,
        List<ConsentType> missingLocalConsentTypes
) {
}