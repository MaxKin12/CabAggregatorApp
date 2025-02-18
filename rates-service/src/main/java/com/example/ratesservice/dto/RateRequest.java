package com.example.ratesservice.dto;

import com.example.ratesservice.enums.AuthorType;
import com.example.ratesservice.enums.annotation.AuthorTypeValidation;
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

        @NotBlank(message = "{validate.field.author.blank}")
        @AuthorTypeValidation(enumClass = AuthorType.class, message = "{validate.field.author.invalid.pattern}")
        String author,

        @NotNull(message = "{validate.field.value.not.null}")
        @Min(1)
        @Max(5)
        Integer value,

        @Size(max = 65535, message = "{validate.field.comment.too-long}")
        String comment

) {
}
