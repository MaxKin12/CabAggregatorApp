package com.example.driverservice.dto.driver;

import static com.example.driverservice.utility.constants.InternationalizationValidationVariablesConstants.EMAIL_BLANK;
import static com.example.driverservice.utility.constants.InternationalizationValidationVariablesConstants.EMAIL_INVALID_PATTERN;
import static com.example.driverservice.utility.constants.InternationalizationValidationVariablesConstants.EMAIL_TOO_LONG;
import static com.example.driverservice.utility.constants.InternationalizationValidationVariablesConstants.GENDER_BLANK;
import static com.example.driverservice.utility.constants.InternationalizationValidationVariablesConstants.NAME_BLANK;
import static com.example.driverservice.utility.constants.InternationalizationValidationVariablesConstants.NAME_TOO_LONG;
import static com.example.driverservice.utility.constants.InternationalizationValidationVariablesConstants.PHONE_BLANK;
import static com.example.driverservice.utility.constants.InternationalizationValidationVariablesConstants.PHONE_INVALID_PATTERN;
import static com.example.driverservice.utility.constants.InternationalizationValidationVariablesConstants.PHONE_TOO_LONG;
import static com.example.driverservice.utility.constants.RegularExpressionsConstants.PHONE_NUMBER_CHECK;

import com.example.driverservice.enums.UserGender;
import com.example.driverservice.enums.annotation.UserGenderValidation;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

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

) {
}
