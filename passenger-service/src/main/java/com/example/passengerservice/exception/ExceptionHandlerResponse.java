package com.example.passengerservice.exception;

public record ExceptionHandlerResponse(

    int statusCode,

    String message

) {
}
