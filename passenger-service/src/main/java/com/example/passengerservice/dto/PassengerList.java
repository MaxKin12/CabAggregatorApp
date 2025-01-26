package com.example.passengerservice.dto;

import java.util.List;

public record PassengerList(
        List<PassengerResponse> passengerList
) {}
