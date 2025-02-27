package com.example.ridesservice.dto.ride.request;

import static com.example.ridesservice.utility.constants.InternationalizationValidationPropertyVariablesConstants.CAR_ID_NEGATIVE;
import static com.example.ridesservice.utility.constants.InternationalizationValidationPropertyVariablesConstants.DESTINATION_ADDRESS_BLANK;
import static com.example.ridesservice.utility.constants.InternationalizationValidationPropertyVariablesConstants.DESTINATION_ADDRESS_TOO_LONG;
import static com.example.ridesservice.utility.constants.InternationalizationValidationPropertyVariablesConstants.DRIVER_ID_NEGATIVE;
import static com.example.ridesservice.utility.constants.InternationalizationValidationPropertyVariablesConstants.PASSENGER_ID_NEGATIVE;
import static com.example.ridesservice.utility.constants.InternationalizationValidationPropertyVariablesConstants.PASSENGER_ID_NULL;
import static com.example.ridesservice.utility.constants.InternationalizationValidationPropertyVariablesConstants.PICK_UP_ADDRESS_BLANK;
import static com.example.ridesservice.utility.constants.InternationalizationValidationPropertyVariablesConstants.PICK_UP_ADDRESS_TOO_LONG;
import static com.example.ridesservice.utility.constants.InternationalizationValidationPropertyVariablesConstants.STATUS_BLANK;

import com.example.ridesservice.enums.RideStatus;
import com.example.ridesservice.enums.annotation.StatusValidation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record RideRequest(

        @NotNull(message = PASSENGER_ID_NULL)
        @Positive(message = PASSENGER_ID_NEGATIVE)
        Long passengerId,

        @Positive(message = DRIVER_ID_NEGATIVE)
        Long driverId,

        @Positive(message = CAR_ID_NEGATIVE)
        Long carId,

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
