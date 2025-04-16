package com.example.ridesservice.dto.ride.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Builder;

@Builder
public record RideResponse(

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
