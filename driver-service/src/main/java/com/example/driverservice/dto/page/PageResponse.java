package com.example.driverservice.dto.page;

import com.example.driverservice.dto.EntityResponse;
import java.util.List;
import lombok.Builder;

@Builder
public record PageResponse(

        List<? extends EntityResponse> list,
        int currentPageNumber,
        int pageLimit,
        int totalPages,
        int totalElements

) {
}
