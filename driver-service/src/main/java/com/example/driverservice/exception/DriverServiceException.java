package com.example.driverservice.exception;

public record DriverServiceException (
        int statusCode,
        String message
) {}
