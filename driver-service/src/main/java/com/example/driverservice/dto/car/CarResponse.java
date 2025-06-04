package com.example.driverservice.dto.car;

import com.example.driverservice.dto.EntityResponse;
import java.util.UUID;
import lombok.Builder;

@Builder
public record CarResponse(

        UUID id,
        String brand,
        String number,
        String color,
        UUID driverId

) implements EntityResponse {
}
