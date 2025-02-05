package com.example.ridesservice.exception;

public record ExceptionHandlerResponse(
        int statusCode,
        String message
) {}
