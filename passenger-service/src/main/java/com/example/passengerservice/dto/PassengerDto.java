package com.example.passengerservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PassengerDto (
        Long id,
        @NotBlank(message = "Name is a necessary field")
        @Size(max = 50, message = "Name is to long")
        String name,
        @NotBlank(message = "Email is a necessary field")
        @Size(max = 100, message = "Email is to long")
        @Email(message = "Invalid email entered")
        String email,
        @NotBlank(message = "Phone is a necessary field")
        @Size(max = 20, message = "Phone number is to long")
        String phone,
        @NotNull(message = "Password is a necessary field")
        String password
) {}
