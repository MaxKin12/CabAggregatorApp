package com.example.driverservice.exception;

public record ExceptionHandlerResponse(

        int statusCode,

        String message

) {}
