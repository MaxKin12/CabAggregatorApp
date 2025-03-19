package com.example.ratesservice.client.dto;

import java.math.BigDecimal;
import java.util.List;

public record DriverResponse(

        Long id,
        String name,
        String email,
        String phone,
        String gender,
        BigDecimal rate,
        List<Long> carIds

) {
}
