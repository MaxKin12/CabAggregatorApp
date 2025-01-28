package com.example.passengerservice.dto;

import java.util.List;

public record PassengerResponseList(
        List<PassengerResponse> passengerList
) {}
