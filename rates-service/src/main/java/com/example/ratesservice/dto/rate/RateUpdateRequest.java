package com.example.ratesservice.dto.rate;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RateUpdateRequest(

    @NotNull(message = "{validate.field.value.not.null}")
    @Min(1)
    @Max(5)
    Integer value,

    @Size(max = 65535, message = "{validate.field.comment.too-long}")
    String comment

) {
}
