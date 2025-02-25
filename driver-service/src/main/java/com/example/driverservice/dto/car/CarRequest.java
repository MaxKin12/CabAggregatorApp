package com.example.driverservice.dto.car;

import static com.example.driverservice.utility.constants.InternationalizationValidationVariablesConstants.BRAND_BLANK;
import static com.example.driverservice.utility.constants.InternationalizationValidationVariablesConstants.BRAND_TOO_LONG;
import static com.example.driverservice.utility.constants.InternationalizationValidationVariablesConstants.COLOR_BLANK;
import static com.example.driverservice.utility.constants.InternationalizationValidationVariablesConstants.COLOR_TOO_LONG;
import static com.example.driverservice.utility.constants.InternationalizationValidationVariablesConstants.DRIVER_ID_NEGATIVE;
import static com.example.driverservice.utility.constants.InternationalizationValidationVariablesConstants.DRIVER_ID_NOT_NULL;
import static com.example.driverservice.utility.constants.InternationalizationValidationVariablesConstants.NUMBER_BLANK;
import static com.example.driverservice.utility.constants.InternationalizationValidationVariablesConstants.NUMBER_INVALID_PATTERN;
import static com.example.driverservice.utility.constants.InternationalizationValidationVariablesConstants.NUMBER_TOO_LONG;
import static com.example.driverservice.utility.constants.RegularExpressionsConstants.CAR_NUMBER_CHECK;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record CarRequest(

        @NotBlank(message = BRAND_BLANK)
        @Size(max = 30, message = BRAND_TOO_LONG)
        String brand,

        @NotBlank(message = NUMBER_BLANK)
        @Size(max = 8, message = NUMBER_TOO_LONG)
        @Pattern(regexp = CAR_NUMBER_CHECK, message = NUMBER_INVALID_PATTERN)
        String number,

        @NotBlank(message = COLOR_BLANK)
        @Size(max = 20, message = COLOR_TOO_LONG)
        String color,

        @NotNull(message = DRIVER_ID_NOT_NULL)
        @Positive(message = DRIVER_ID_NEGATIVE)
        Long driverId

) {
}
