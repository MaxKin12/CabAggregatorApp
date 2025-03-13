package com.example.ridesservice.client.dto;

import lombok.Builder;

@Builder
public record PassengerResponse(

        Long id,
        String name,
        String email,
        String phone

) {
}
