package com.example.driverservice.dto.car;

import static com.example.driverservice.utility.constants.RegularExpressionsConstants.CAR_NUMBER_CHECK;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record CarRequest(

        @NotBlank(message = "{validate.field.brand.blank}")
        @Size(max = 30, message = "{validate.field.brand.too-long}")
        String brand,

        @NotBlank(message = "{validate.field.number.blank}")
        @Size(max = 8, message = "{validate.field.number.too-long}")
        @Pattern(regexp = CAR_NUMBER_CHECK, message = "{validate.field.number.invalid.pattern")
        String number,

        @NotBlank(message = "{validate.field.color.blank}")
        @Size(max = 20, message = "{validate.field.color.too-long}")
        String color,

        @NotNull(message = "{validate.field.driver-id.not.null}")
        @Positive(message = "{validate.field.driver-id.negative}")
        Long driverId

) {
}
