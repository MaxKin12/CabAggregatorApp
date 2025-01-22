package com.example.passengerservice.dto;

import java.util.List;

public record PassengerListDto (
        List<PassengerResponseDto> passengerList
) {}
