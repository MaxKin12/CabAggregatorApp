package com.example.ratesservice.dto.rate.request;

import static com.example.ratesservice.utility.constants.InternationalizationValidationPropertyVariablesConstants.COMMENT_TOO_LONG;
import static com.example.ratesservice.utility.constants.InternationalizationValidationPropertyVariablesConstants.VALUE_NULL;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record RateUpdateRequest(

        @NotNull(message = VALUE_NULL)
        @Min(1)
        @Max(5)
        Integer value,

        @Size(max = 65535, message = COMMENT_TOO_LONG)
        String comment

) {
}
