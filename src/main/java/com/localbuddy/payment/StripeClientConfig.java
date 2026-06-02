package com.localbuddy.payment;

import com.localbuddy.common.exception.BadRequestException;
import com.stripe.StripeClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StripeClientConfig {

    @Bean
    public StripeClient stripeClient(StripeProperties stripeProperties) {
        if (stripeProperties.secretKey() == null ||
                stripeProperties.secretKey().trim().isEmpty()) {
            throw new BadRequestException("Stripe secret key is not configured");
        }

        return new StripeClient(stripeProperties.secretKey().trim());
    }
}