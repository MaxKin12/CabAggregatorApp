package com.example.passengerservice.dto;

public record PassengerResponseDto(
        Long id,
        String name,
        String email,
        String phone
) {}
