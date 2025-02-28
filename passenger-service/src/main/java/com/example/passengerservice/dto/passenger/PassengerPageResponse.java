package com.example.passengerservice.dto.passenger;

import lombok.Builder;

import java.util.List;

@Builder
public record PassengerPageResponse(

        List<PassengerResponse> passengerList,
        int currentPageNumber,
        int pageLimit,
        int totalPages,
        int totalElements

) {
}
