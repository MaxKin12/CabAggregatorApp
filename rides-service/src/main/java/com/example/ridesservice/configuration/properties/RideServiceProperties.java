package com.example.ridesservice.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "ride-service")
public record RideServiceProperties(

        Integer maxPageLimit

) {
}
