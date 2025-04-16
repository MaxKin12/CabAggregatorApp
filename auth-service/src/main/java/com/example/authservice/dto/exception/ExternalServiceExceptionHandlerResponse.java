package com.example.authservice.dto.exception;

import java.time.LocalDateTime;

public record ExternalServiceExceptionHandlerResponse(

        int statusCode,
        String message,
        LocalDateTime localDateTime

) {
}
