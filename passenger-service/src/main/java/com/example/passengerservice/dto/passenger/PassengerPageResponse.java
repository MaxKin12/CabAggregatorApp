package com.example.passengerservice.dto.passenger;

import java.util.List;

public record PassengerPageResponse(

        List<PassengerResponse> passengerList,
        int currentPageNumber,
        int pageLimit,
        int totalPages,
        int totalElements

) {
}
