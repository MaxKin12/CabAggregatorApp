package com.example.ridesservice.dto.ride.request;

import java.util.UUID;
import lombok.Builder;

@Builder
public record RideDriverSettingRequest(

        UUID driverId,
        UUID carId

) {
}

