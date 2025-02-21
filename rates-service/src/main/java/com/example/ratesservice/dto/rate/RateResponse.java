package com.example.ratesservice.dto.rate;

import lombok.Builder;

@Builder
public record RateResponse(

        Long id,

        Long rideId,

        Long passengerId,

        Long driverId,

        String recipient,

        Integer value,

        String comment

) {
}
