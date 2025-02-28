package com.example.ratesservice.dto.rate;

import java.math.BigDecimal;

public record RateAverageResponse(

        Long personId,
        BigDecimal averageValue

) {
}
