package com.example.driverservice.dto.car;

import com.example.driverservice.dto.EntityResponse;
import lombok.Builder;

import java.util.UUID;

@Builder
public record CarResponse(

        Long id,
        String brand,
        String number,
        String color,
        UUID driverId

) implements EntityResponse {
}
