package com.example.driverservice.dto.driver;

import java.util.List;
import lombok.Builder;

@Builder
public record DriverResponse(
        Long id,
        String name,
        String email,
        String phone,
        String gender,
        List<Long> carIds
) {}
