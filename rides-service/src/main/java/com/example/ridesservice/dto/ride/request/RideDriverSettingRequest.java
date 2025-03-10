package com.example.ridesservice.dto.ride.request;

import static com.example.ridesservice.utility.constants.InternationalizationValidationPropertyVariablesConstants.CAR_ID_NEGATIVE;
import static com.example.ridesservice.utility.constants.InternationalizationValidationPropertyVariablesConstants.DRIVER_ID_NEGATIVE;

import jakarta.validation.constraints.Positive;
import lombok.Builder;

@Builder
public record RideDriverSettingRequest(

        @Positive(message = DRIVER_ID_NEGATIVE)
        Long driverId,

        @Positive(message = CAR_ID_NEGATIVE)
        Long carId

) {
}

