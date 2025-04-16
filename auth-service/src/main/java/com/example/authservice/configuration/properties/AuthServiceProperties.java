package com.example.authservice.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "auth-service")
public record AuthServiceProperties(

        Integer maxPageLimit

) {
}
