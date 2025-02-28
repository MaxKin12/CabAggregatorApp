package com.example.passengerservice.dto.passenger;

import java.math.BigDecimal;

public record PassengerResponse(

        Long id,
        String name,
        String email,
        String phone,
        BigDecimal rate

) {
}
