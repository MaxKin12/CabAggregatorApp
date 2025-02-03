package com.example.ridesservice.exception;

public record RideException (
        int statusCode,
        String message
) {}
