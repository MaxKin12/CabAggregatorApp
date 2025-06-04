package com.example.passengerservice.dto.passenger;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.Builder;

@Builder
public record PassengerResponse(

        UUID id,
        String name,
        String email,
        String phone,
        BigDecimal rate

) {
}
