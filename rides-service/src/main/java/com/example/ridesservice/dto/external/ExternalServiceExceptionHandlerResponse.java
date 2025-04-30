package com.example.ridesservice.dto.external;

import java.time.LocalDateTime;

public record ExternalServiceExceptionHandlerResponse(

        int statusCode,
        String message,
        LocalDateTime localDateTime

) {
}
