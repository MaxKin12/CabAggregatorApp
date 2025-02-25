package com.example.ridesservice.dto.request;

import jakarta.validation.constraints.Positive;

public record RideDriverSettingRequest(

        @Positive(message = "{validate.field.driver-id.negative}")
        Long driverId,

        @Positive(message = "{validate.field.car-id.negative}")
        Long carId

) {
}

