package com.example.ratesservice.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "rate-service")
public record RateServiceProperties(

        Integer lastRidesCount,
        Integer maxPageLimit

) {
}
