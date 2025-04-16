package com.example.ratesservice.client.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Builder;

@Builder
public record RidesResponse(

        Long id,
        UUID passengerId,
        UUID driverId,
        Long carId,
        String pickUpAddress,
        String destinationAddress,
        String status,
        LocalDateTime orderTime,
        BigDecimal price

) {
}
