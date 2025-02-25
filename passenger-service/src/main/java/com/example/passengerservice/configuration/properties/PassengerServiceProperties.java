package com.example.passengerservice.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "passenger-service")
public record PassengerServiceProperties(

        Integer maxPageLimit

) {
}
