package com.example.ratesservice.dto.rate.request;

import static com.example.ratesservice.utility.constants.InternationalizationValidationPropertyVariablesConstants.COMMENT_TOO_LONG;
import static com.example.ratesservice.utility.constants.InternationalizationValidationPropertyVariablesConstants.RECIPIENT_BLANK;
import static com.example.ratesservice.utility.constants.InternationalizationValidationPropertyVariablesConstants.RIDE_ID_NULL;
import static com.example.ratesservice.utility.constants.InternationalizationValidationPropertyVariablesConstants.VALUE_NULL;

import com.example.ratesservice.enums.RecipientType;
import com.example.ratesservice.enums.annotation.RecipientTypeValidation;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;
import lombok.Builder;

@Builder
public record RateRequest(

        UUID passengerId,

        @NotNull(message = RIDE_ID_NULL)
        UUID rideId,

        UUID driverId,

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
