package com.example.ratesservice.client.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record DriverResponse(

        UUID id,
        String name,
        String email,
        String phone,
        String gender,
        BigDecimal rate,
        List<Long> carIds

) {
}
