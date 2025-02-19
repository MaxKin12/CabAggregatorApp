package com.example.ratesservice.client.ride.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record RidesResponse(

        Long id,

        Long passengerId,

        Long driverId,

        Long carId,

        String pickUpAddress,

        String destinationAddress,

        String status,

        LocalDateTime orderTime,

        BigDecimal price

) {
}
