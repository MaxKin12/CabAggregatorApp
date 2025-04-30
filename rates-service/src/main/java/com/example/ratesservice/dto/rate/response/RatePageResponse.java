package com.example.ratesservice.dto.rate.response;

import java.util.List;
import lombok.Builder;

@Builder
public record RatePageResponse(

        List<RateResponse> rateList,
        int currentPageNumber,
        int pageLimit,
        int totalPages,
        int totalElements

) {
}
