package com.localbuddy.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI localBuddyOpenAPI() {
        return new OpenAPI().info(new Info()
                .title("LocalBuddy API")
                .version("1.0")
                .description("LocalBuddy backend API documentation"));
    }
}