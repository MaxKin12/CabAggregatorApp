package com.example.authservice.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "ride-service")
public record AuthProperties(

        Integer maxPageLimit

) {
}
