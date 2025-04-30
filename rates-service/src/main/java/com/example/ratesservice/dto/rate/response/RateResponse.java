package com.example.ratesservice.dto.rate.response;

import java.util.UUID;
import lombok.Builder;

@Builder
public record RateResponse(

        UUID id,
        UUID rideId,
        UUID passengerId,
        UUID driverId,
        String recipient,
        Integer value,
        String comment

) {
}
