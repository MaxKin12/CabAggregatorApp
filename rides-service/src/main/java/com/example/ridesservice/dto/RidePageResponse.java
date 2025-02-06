package com.example.ridesservice.dto;

import java.util.List;

public record RidePageResponse(

        List<RideResponse> rideList,

        int currentPageNumber,

        int pageLimit,

        int totalPages,

        int totalElements

) {
}
