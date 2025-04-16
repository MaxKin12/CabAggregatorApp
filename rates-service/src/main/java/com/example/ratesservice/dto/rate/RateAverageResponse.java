package com.example.ratesservice.dto.rate;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.Builder;

@Builder
public record RateAverageResponse(

        UUID personId,
        BigDecimal averageValue

) {
}
