package com.example.ridesservice.client.dto;

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
