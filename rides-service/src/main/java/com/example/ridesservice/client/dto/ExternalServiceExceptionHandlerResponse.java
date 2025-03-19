package com.example.ridesservice.client.dto;

import java.time.LocalDateTime;

public record ExternalServiceExceptionHandlerResponse(

        int statusCode,
        String message,
        LocalDateTime localDateTime

) {
}
