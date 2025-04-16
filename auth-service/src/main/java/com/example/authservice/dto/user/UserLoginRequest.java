package com.example.authservice.dto.user;

import static com.example.authservice.utility.constants.InternationalizationValidationPropertyVariablesConstants.NAME_BLANK;
import static com.example.authservice.utility.constants.InternationalizationValidationPropertyVariablesConstants.NAME_TOO_LONG;
import static com.example.authservice.utility.constants.InternationalizationValidationPropertyVariablesConstants.PASSWORD_BLANK;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record UserLoginRequest(

        @NotBlank(message = NAME_BLANK)
        @Size(max = 50, message = NAME_TOO_LONG)
        String username,

        @NotBlank(message = PASSWORD_BLANK)
        String password

) {
}
