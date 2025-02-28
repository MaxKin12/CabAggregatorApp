package com.example.ridesservice.client.dto;

public record PassengerResponse(

        Long id,
        String name,
        String email,
        String phone

) {
}
