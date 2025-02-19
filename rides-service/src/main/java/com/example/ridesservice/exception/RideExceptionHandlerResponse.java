package com.example.ridesservice.exception;

public record RideExceptionHandlerResponse(

        int statusCode,

        String message

) {
}
