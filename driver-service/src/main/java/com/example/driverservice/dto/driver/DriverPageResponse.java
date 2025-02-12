package com.example.driverservice.dto.driver;

import java.util.List;

public record DriverPageResponse(

        List<DriverResponse> driverList,

        int currentPageNumber,

        int pageLimit,

        int totalPages,

        int totalElements

) {
}
