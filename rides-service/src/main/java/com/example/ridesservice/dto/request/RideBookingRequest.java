package com.example.ridesservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record RideBookingRequest(

        @NotNull(message = "{validate.field.passenger-id.not.null}")
        @Positive(message = "{validate.field.passenger-id.negative}")
        Long passengerId,

        @NotBlank(message = "{validate.field.pick-up-address.blank}")
        @Size(max = 100, message = "{validate.field.pick-up-address.too-long}")
        String pickUpAddress,

        @NotBlank(message = "{validate.field.destination-address.blank}")
        @Size(max = 100, message = "{validate.field.destination-address.too-long}")
        String destinationAddress

) {
}
