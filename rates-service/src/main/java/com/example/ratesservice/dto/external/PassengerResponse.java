package com.example.ratesservice.dto.external;

import java.math.BigDecimal;
import java.util.UUID;

public record PassengerResponse(

        UUID id,
        String name,
        String email,
        String phone,
        BigDecimal rate

) {
}
