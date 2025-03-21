package com.example.driverservice.dto.driver;

import static com.example.driverservice.utility.constants.InternationalizationValidationPropertyVariablesConstants.EMAIL_BLANK;
import static com.example.driverservice.utility.constants.InternationalizationValidationPropertyVariablesConstants.EMAIL_INVALID_PATTERN;
import static com.example.driverservice.utility.constants.InternationalizationValidationPropertyVariablesConstants.EMAIL_TOO_LONG;
import static com.example.driverservice.utility.constants.InternationalizationValidationPropertyVariablesConstants.GENDER_BLANK;
import static com.example.driverservice.utility.constants.InternationalizationValidationPropertyVariablesConstants.NAME_BLANK;
import static com.example.driverservice.utility.constants.InternationalizationValidationPropertyVariablesConstants.NAME_TOO_LONG;
import static com.example.driverservice.utility.constants.InternationalizationValidationPropertyVariablesConstants.PHONE_BLANK;
import static com.example.driverservice.utility.constants.InternationalizationValidationPropertyVariablesConstants.PHONE_INVALID_PATTERN;
import static com.example.driverservice.utility.constants.InternationalizationValidationPropertyVariablesConstants.PHONE_TOO_LONG;
import static com.example.driverservice.utility.constants.RegularExpressionsConstants.PHONE_NUMBER_CHECK;

import com.example.driverservice.dto.EntityRequest;
import com.example.driverservice.dto.EntityResponse;
import com.example.driverservice.enums.UserGender;
import com.example.driverservice.enums.annotation.UserGenderValidation;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record DriverRequest(

        @NotBlank(message = NAME_BLANK)
        @Size(max = 50, message = NAME_TOO_LONG)
        String name,

        @NotBlank(message = EMAIL_BLANK)
        @Size(max = 100, message = EMAIL_TOO_LONG)
        @Email(message = EMAIL_INVALID_PATTERN)
        String email,

        @NotBlank(message = PHONE_BLANK)
        @Size(max = 13, message = PHONE_TOO_LONG)
        @Pattern(regexp = PHONE_NUMBER_CHECK, message = PHONE_INVALID_PATTERN)
        String phone,

        @NotBlank(message = GENDER_BLANK)
        @UserGenderValidation(enumClass = UserGender.class)
        String gender

) implements EntityRequest {
}
