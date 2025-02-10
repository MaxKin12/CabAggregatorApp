package com.example.ratesservice.dto;

import java.math.BigDecimal;

public record RateAverageResponse(

        Long personId,

        BigDecimal averageValue

) {
}
