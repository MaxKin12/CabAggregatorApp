package com.example.driverservice.dto.driver;

import lombok.Builder;

import java.util.List;

@Builder
public record DriverResponse(
        Long id,
        String name,
        String email,
        String phone,
        String gender,
        List<Long> carIds
) {}
