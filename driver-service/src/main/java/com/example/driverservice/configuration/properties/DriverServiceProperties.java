package com.example.driverservice.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "driver-service")
public record DriverServiceProperties(

        Integer maxPageLimit

) {
}
