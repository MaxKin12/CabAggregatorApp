package com.example.ridesservice.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "timetravel")
public record TimetravelRouteProperties(

        String appId,

        String apiKey,

        String countryCode

) {
}
