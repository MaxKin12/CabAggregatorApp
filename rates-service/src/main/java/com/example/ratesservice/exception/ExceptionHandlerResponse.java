package com.example.ratesservice.exception;

public record ExceptionHandlerResponse(

        int statusCode,

        String message

) {
}
