package com.example.ratesservice.dto;

import lombok.Builder;

@Builder
public record RateResponse(

        Long id,

        Long passengerId,

        Long driverId,

        String author,

        Integer value,

        String comment

) {
}
