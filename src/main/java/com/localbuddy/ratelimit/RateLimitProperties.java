package com.localbuddy.ratelimit;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.rate-limit.public-api")
public record RateLimitProperties(
        boolean enabled,
        int maxRequests,
        int windowSeconds
) {
}