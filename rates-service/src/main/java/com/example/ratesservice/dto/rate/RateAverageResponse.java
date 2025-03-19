package com.example.ratesservice.dto.rate;

import java.math.BigDecimal;
import lombok.Builder;

@Builder
public record RateAverageResponse(

        Long personId,
        BigDecimal averageValue

) {
}
