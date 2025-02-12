package com.example.ridesservice.dto;

import com.example.ridesservice.enums.RideStatus;
import com.example.ridesservice.enums.annotation.StatusValidation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record RideRequest(

        @NotNull(message = "{validate.field.passenger-id.not.null}")
        @Positive(message = "{validate.field.passenger-id.negative}")
        Long passengerId,

        @Positive(message = "{validate.field.driver-id.negative}")
        Long driverId,

        @Positive(message = "{validate.field.car-id.negative}")
        Long carId,

        @NotBlank(message = "{validate.field.pick-up-address.blank}")
        @Size(max = 100, message = "{validate.field.pick-up-address.too-long}")
        String pickUpAddress,

        @NotBlank(message = "{validate.field.destination-address.blank}")
        @Size(max = 100, message = "{validate.field.destination-address.too-long}")
        String destinationAddress,

        @NotBlank(message = "{validate.field.status.blank}")
        @StatusValidation(enumClass = RideStatus.class, message = "{validate.field.status.invalid.pattern}")
        String status

) {
}
