package com.example.driverservice.dto.driver;

import com.example.driverservice.enums.Sex;

import java.util.List;

public record DriverResponse(
        Long id,
        String name,
        String email,
        String phone,
        String sex,
        List<Long> carIds
) {}
