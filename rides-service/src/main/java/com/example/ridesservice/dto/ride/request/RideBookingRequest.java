package com.example.ridesservice.dto.ride.request;

import static com.example.ridesservice.utility.constants.InternationalizationValidationPropertyVariablesConstants.DESTINATION_ADDRESS_BLANK;
import static com.example.ridesservice.utility.constants.InternationalizationValidationPropertyVariablesConstants.DESTINATION_ADDRESS_TOO_LONG;
import static com.example.ridesservice.utility.constants.InternationalizationValidationPropertyVariablesConstants.PASSENGER_ID_NEGATIVE;
import static com.example.ridesservice.utility.constants.InternationalizationValidationPropertyVariablesConstants.PASSENGER_ID_NULL;
import static com.example.ridesservice.utility.constants.InternationalizationValidationPropertyVariablesConstants.PICK_UP_ADDRESS_BLANK;
import static com.example.ridesservice.utility.constants.InternationalizationValidationPropertyVariablesConstants.PICK_UP_ADDRESS_TOO_LONG;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record RideBookingRequest(

        @NotNull(message = PASSENGER_ID_NULL)
        @Positive(message = PASSENGER_ID_NEGATIVE)
        Long passengerId,

        @NotBlank(message = PICK_UP_ADDRESS_BLANK)
        @Size(max = 100, message = PICK_UP_ADDRESS_TOO_LONG)
        String pickUpAddress,

        @NotBlank(message = DESTINATION_ADDRESS_BLANK)
        @Size(max = 100, message = DESTINATION_ADDRESS_TOO_LONG)
        String destinationAddress

) {
}
