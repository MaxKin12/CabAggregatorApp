package com.example.passengerservice.exception;

public record PassengerException (
    int statusCode,
    String message
) {}
