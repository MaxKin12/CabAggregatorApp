package com.example.ridesservice.dto.external;

import java.util.UUID;

public record CarResponse(

        UUID id,
        String brand,
        String number,
        String color,
        UUID driverId

) {
}
