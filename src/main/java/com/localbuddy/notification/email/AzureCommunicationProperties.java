package com.localbuddy.notification.email;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.azure.communication")
public record AzureCommunicationProperties(
        String connectionString
) {
}