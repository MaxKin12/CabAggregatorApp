package com.example.ridesservice.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder()
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
