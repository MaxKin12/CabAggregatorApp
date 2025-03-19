package com.example.ridesservice.client.dto;

import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;

@Builder
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
