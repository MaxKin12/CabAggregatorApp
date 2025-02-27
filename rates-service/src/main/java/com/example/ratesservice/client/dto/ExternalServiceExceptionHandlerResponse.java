package com.example.ratesservice.client.dto;

public record ExternalServiceExceptionHandlerResponse(

        int statusCode,

        String message

) {
}
