package com.example.passengerservice.dto.exception;

import java.time.LocalDateTime;

public record PassengerExceptionHandlerResponse(

        int statusCode,
        String message,
        LocalDateTime localDateTime

) {
}
