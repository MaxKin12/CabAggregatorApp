package com.example.driverservice.dto.car;

import com.example.driverservice.dto.EntityResponse;
import lombok.Builder;

@Builder
public record CarResponse(

        Long id,
        String brand,
        String number,
        String color,
        Long driverId

) implements EntityResponse {
}
