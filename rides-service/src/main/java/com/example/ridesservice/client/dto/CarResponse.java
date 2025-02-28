package com.example.ridesservice.client.dto;

public record CarResponse(

        Long id,
        String brand,
        String number,
        String color,
        Long driverId

) {
}
