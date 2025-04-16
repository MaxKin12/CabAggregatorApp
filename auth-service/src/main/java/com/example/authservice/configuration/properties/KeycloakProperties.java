package com.example.authservice.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "keycloak")
public record KeycloakProperties(

        String authServerUrl,
        String adminClientId,
        String adminClientSecret,
        String realm,
        String client

) {
}
