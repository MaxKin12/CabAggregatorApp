package com.example.ratesservice.mapper;

import com.example.ratesservice.dto.RatePageResponse;
import com.example.ratesservice.dto.RateResponse;
import com.example.ratesservice.model.Rate;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = RateMapper.class
)
public interface RatePageMapper {

    @Mapping(target = "rateList", source = "ratePage", qualifiedByName = "ratePageToRateResponseList")
    @Mapping(target = "currentPageNumber", source = "offset")
    @Mapping(target = "pageLimit", source = "limit")
    @Mapping(target = "totalPages", source = "ratePage.totalPages")
    @Mapping(target = "totalElements", source = "ratePage.totalElements")
    RatePageResponse toResponsePage(Page<Rate> ratePage, int offset, int limit);

    @Named("ratePageToRateResponseList")
    List<RateResponse> ratePageToRateResponseList(Page<Rate> ratePage);

}
