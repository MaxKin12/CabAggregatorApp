package com.example.driverservice.dto.car;

import static com.example.driverservice.utility.constants.InternationalizationValidationPropertyVariablesConstants.BRAND_BLANK;
import static com.example.driverservice.utility.constants.InternationalizationValidationPropertyVariablesConstants.BRAND_TOO_LONG;
import static com.example.driverservice.utility.constants.InternationalizationValidationPropertyVariablesConstants.COLOR_BLANK;
import static com.example.driverservice.utility.constants.InternationalizationValidationPropertyVariablesConstants.COLOR_TOO_LONG;
import static com.example.driverservice.utility.constants.InternationalizationValidationPropertyVariablesConstants.DRIVER_ID_NEGATIVE;
import static com.example.driverservice.utility.constants.InternationalizationValidationPropertyVariablesConstants.DRIVER_ID_NULL;
import static com.example.driverservice.utility.constants.InternationalizationValidationPropertyVariablesConstants.NUMBER_BLANK;
import static com.example.driverservice.utility.constants.InternationalizationValidationPropertyVariablesConstants.NUMBER_INVALID_PATTERN;
import static com.example.driverservice.utility.constants.InternationalizationValidationPropertyVariablesConstants.NUMBER_TOO_LONG;
import static com.example.driverservice.utility.constants.RegularExpressionsConstants.CAR_NUMBER_CHECK;

import com.example.driverservice.dto.EntityRequest;
import com.example.driverservice.dto.EntityResponse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
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

        @NotNull(message = DRIVER_ID_NULL)
        @Positive(message = DRIVER_ID_NEGATIVE)
        Long driverId

) implements EntityRequest {
}
