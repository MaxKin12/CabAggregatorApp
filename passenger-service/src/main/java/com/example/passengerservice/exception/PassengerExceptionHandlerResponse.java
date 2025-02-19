package com.example.passengerservice.exception;

public record PassengerExceptionHandlerResponse(

    int statusCode,

    String message

) {
}
