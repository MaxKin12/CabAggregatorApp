package com.example.ratesservice.dto.rate;

import static com.example.ratesservice.utility.constants.InternationalizationValidationPropertyVariablesConstants.COMMENT_TOO_LONG;
import static com.example.ratesservice.utility.constants.InternationalizationValidationPropertyVariablesConstants.DRIVER_ID_NEGATIVE;
import static com.example.ratesservice.utility.constants.InternationalizationValidationPropertyVariablesConstants.DRIVER_ID_NULL;
import static com.example.ratesservice.utility.constants.InternationalizationValidationPropertyVariablesConstants.PASSENGER_ID_NEGATIVE;
import static com.example.ratesservice.utility.constants.InternationalizationValidationPropertyVariablesConstants.PASSENGER_ID_NULL;
import static com.example.ratesservice.utility.constants.InternationalizationValidationPropertyVariablesConstants.RECIPIENT_BLANK;
import static com.example.ratesservice.utility.constants.InternationalizationValidationPropertyVariablesConstants.RIDE_ID_NEGATIVE;
import static com.example.ratesservice.utility.constants.InternationalizationValidationPropertyVariablesConstants.RIDE_ID_NULL;
import static com.example.ratesservice.utility.constants.InternationalizationValidationPropertyVariablesConstants.VALUE_NULL;

import com.example.ratesservice.enums.RecipientType;
import com.example.ratesservice.enums.annotation.RecipientTypeValidation;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record RateRequest(

        @NotNull(message = PASSENGER_ID_NULL)
        @Positive(message = PASSENGER_ID_NEGATIVE)
        Long passengerId,

        @NotNull(message = RIDE_ID_NULL)
        @Positive(message = RIDE_ID_NEGATIVE)
        Long rideId,

        @NotNull(message = DRIVER_ID_NULL)
        @Positive(message = DRIVER_ID_NEGATIVE)
        Long driverId,

        @NotBlank(message = RECIPIENT_BLANK)
        @RecipientTypeValidation(enumClass = RecipientType.class)
        String recipient,

        @NotNull(message = VALUE_NULL)
        @Min(1)
        @Max(5)
        Integer value,

        @Size(max = 65535, message = COMMENT_TOO_LONG)
        String comment

) {
}
