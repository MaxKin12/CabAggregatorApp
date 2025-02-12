package com.example.driverservice.dto.car;

import java.util.List;

public record CarPageResponse(

        List<CarResponse> carList,

        int currentPageNumber,

        int pageLimit,

        int totalPages,

        int totalElements

) {
}
