package com.example.ridesservice.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record RideResponse(
        Long id,
        Long passengerId,
        Long driverId,
        Long carId,
        String pickupAddress,
        String destinationAddress,
        String status,
        LocalDateTime orderTime,
        BigDecimal price
) {}
