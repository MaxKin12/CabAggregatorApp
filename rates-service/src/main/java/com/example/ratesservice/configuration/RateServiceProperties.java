package com.example.ratesservice.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "rate-service")
public record RateServiceProperties(

        Integer lastRidesCount

) {
}
