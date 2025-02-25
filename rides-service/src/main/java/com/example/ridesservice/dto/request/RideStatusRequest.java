package com.example.ridesservice.dto.request;

import com.example.ridesservice.enums.RideStatus;
import com.example.ridesservice.enums.annotation.StatusValidation;
import jakarta.validation.constraints.NotBlank;

public record RideStatusRequest(

        @NotBlank(message = "{validate.field.status.blank}")
        @StatusValidation(enumClass = RideStatus.class, message = "{validate.field.status.invalid.pattern}")
        String status

) {
}
