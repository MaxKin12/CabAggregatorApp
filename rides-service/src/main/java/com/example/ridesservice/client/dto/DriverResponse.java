package com.example.ridesservice.client.dto;

import java.util.List;

public record DriverResponse(

        Long id,
        String name,
        String email,
        String phone,
        String gender,
        List<Long> carIds

) {
}
