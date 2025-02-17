package com.example.ridesservice.client.passenger.dto;

public record PassengerResponse(

        Long id,

        String name,

        String email,

        String phone

) {
}
