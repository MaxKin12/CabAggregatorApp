package com.example.driverservice.dto.car;

public record CarResponse(
        Long id,
        String brand,
        String number,
        String color,
        Long driverId
) {}
