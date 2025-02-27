package com.example.ridesservice.client.dto;

public record ExternalServiceExceptionHandlerResponse(

        int statusCode,
        String message

) {
}
