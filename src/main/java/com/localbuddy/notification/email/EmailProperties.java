package com.localbuddy.notification.email;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.email")
public record EmailProperties(
        String provider,
        String fromAddress,
        String fromName
) {
}