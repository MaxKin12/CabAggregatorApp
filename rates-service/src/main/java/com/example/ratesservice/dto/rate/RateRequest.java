package com.example.ratesservice.dto.rate;

import com.example.ratesservice.enums.RecipientType;
import com.example.ratesservice.enums.annotation.RecipientTypeValidation;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record RateRequest(

        @NotNull(message = "{validate.field.passenger-id.not.null}")
        @Positive(message = "{validate.field.passenger-id.negative}")
        Long passengerId,

        @NotNull(message = "{validate.field.ride-id.not.null}")
        @Positive(message = "{validate.field.ride-id.negative}")
        Long rideId,

        @NotNull(message = "{validate.field.driver-id.not.null}")
        @Positive(message = "{validate.field.driver-id.negative}")
        Long driverId,

        @NotBlank(message = "{validate.field.recipient.blank}")
        @RecipientTypeValidation(
                enumClass = RecipientType.class,
                message = "{validate.field.recipient.invalid.pattern}"
        )
        String recipient,

        @NotNull(message = "{validate.field.value.not.null}")
        @Min(1)
        @Max(5)
        Integer value,

        @Size(max = 65535, message = "{validate.field.comment.too-long}")
        String comment

) {
}
