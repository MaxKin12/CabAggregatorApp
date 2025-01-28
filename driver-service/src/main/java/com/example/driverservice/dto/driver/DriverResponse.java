package com.example.driverservice.dto.driver;

import com.example.driverservice.model.enums.Sex;

public record DriverResponse(
        Long id,
        String name,
        String email,
        String phone,
        Sex sex,
        Long carId
) {}
