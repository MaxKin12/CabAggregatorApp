package com.example.ratesservice.client.dto;

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
