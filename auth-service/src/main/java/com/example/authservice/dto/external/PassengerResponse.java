package com.example.authservice.dto.external;

import java.math.BigDecimal;
import lombok.Builder;

@Builder
public record PassengerResponse(

        Long id,
        String name,
        String email,
        String phone,
        BigDecimal rate

) {
}
