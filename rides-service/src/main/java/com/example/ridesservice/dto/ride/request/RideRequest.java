package com.example.ridesservice.dto.ride.request;

import static com.example.ridesservice.utility.constants.InternationalizationValidationPropertyVariablesConstants.DESTINATION_ADDRESS_BLANK;
import static com.example.ridesservice.utility.constants.InternationalizationValidationPropertyVariablesConstants.DESTINATION_ADDRESS_TOO_LONG;
import static com.example.ridesservice.utility.constants.InternationalizationValidationPropertyVariablesConstants.PICK_UP_ADDRESS_BLANK;
import static com.example.ridesservice.utility.constants.InternationalizationValidationPropertyVariablesConstants.PICK_UP_ADDRESS_TOO_LONG;
import static com.example.ridesservice.utility.constants.InternationalizationValidationPropertyVariablesConstants.STATUS_BLANK;

import com.example.ridesservice.enums.RideStatus;
import com.example.ridesservice.enums.annotation.StatusValidation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.util.UUID;

@Builder
public record RideRequest(

        UUID passengerId,

        UUID driverId,

        UUID carId,

        @NotBlank(message = PICK_UP_ADDRESS_BLANK)
        @Size(max = 100, message = PICK_UP_ADDRESS_TOO_LONG)
        String pickUpAddress,

        @NotBlank(message = DESTINATION_ADDRESS_BLANK)
        @Size(max = 100, message = DESTINATION_ADDRESS_TOO_LONG)
        String destinationAddress,

        @NotBlank(message = STATUS_BLANK)
        @StatusValidation(enumClass = RideStatus.class)
        String status

) {
}
