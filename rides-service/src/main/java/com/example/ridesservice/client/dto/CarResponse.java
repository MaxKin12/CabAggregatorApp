package com.example.ridesservice.client.dto;

import java.util.UUID;

public record CarResponse(

        Long id,
        String brand,
        String number,
        String color,
        UUID driverId

) {
}
