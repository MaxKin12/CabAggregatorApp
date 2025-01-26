package com.example.passengerservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import static com.example.passengerservice.constant.RegularExpressionsConstants.PHONE_NUMBER_CHECK;

public record PassengerRequest(
        @NotBlank(message = "Name is a necessary field")
        @Size(max = 50, message = "Name is too long")
        String name,
        @NotBlank(message = "Email is a necessary field")
        @Size(max = 100, message = "Email is too long")
        @Email(message = "Invalid email entered")
        String email,
        @NotBlank(message = "Phone is a necessary field")
        @Size(max = 20, message = "Phone number is to long")
        @Pattern(regexp = PHONE_NUMBER_CHECK, message = "Entered phone number format doesn't supported")
        String phone
) {}
