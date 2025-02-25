package com.example.driverservice.dto.page;

import com.example.driverservice.dto.EntityResponse;
import java.util.List;

public record PageResponse<T extends EntityResponse>(

        List<T> list,
        int currentPageNumber,
        int pageLimit,
        int totalPages,
        int totalElements

) {
}
