package com.example.ridesservice.dto.ride.request;

import static com.example.ridesservice.utility.constants.InternationalizationValidationPropertyVariablesConstants.STATUS_BLANK;

import com.example.ridesservice.enums.RideStatus;
import com.example.ridesservice.enums.annotation.StatusValidation;
import jakarta.validation.constraints.NotBlank;

public record RideStatusRequest(

        @NotBlank(message = STATUS_BLANK)
        @StatusValidation(enumClass = RideStatus.class)
        String status

) {
}
