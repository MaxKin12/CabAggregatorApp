package com.example.passengerservice.dto.passenger;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record PassengerResponse(

        Long id,
        String name,
        String email,
        String phone,
        BigDecimal rate

) {
}
