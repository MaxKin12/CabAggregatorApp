package com.example.passengerservice.dto.exception;

import java.time.LocalDateTime;

public record ExceptionHandlerResponse(

        int statusCode,
        String message,
        LocalDateTime localDateTime

) {
}
