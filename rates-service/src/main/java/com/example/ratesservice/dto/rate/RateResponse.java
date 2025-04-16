package com.example.ratesservice.dto.rate;

import lombok.Builder;

import java.util.UUID;

@Builder
public record RateResponse(

        Long id,
        Long rideId,
        UUID passengerId,
        UUID driverId,
        String recipient,
        Integer value,
        String comment

) {
}
