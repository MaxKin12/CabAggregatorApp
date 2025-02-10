package com.example.ratesservice.mapper;

import com.example.ratesservice.dto.RateAverageResponse;
import com.example.ratesservice.dto.RatePageResponse;
import com.example.ratesservice.dto.RateRequest;
import com.example.ratesservice.dto.RateResponse;
import com.example.ratesservice.enums.AuthorType;
import com.example.ratesservice.model.Rate;
import java.math.BigDecimal;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RateMapper {

    @Mapping(target = "author", source = "author", qualifiedByName = "authorToLowerCaseString")
    RateResponse toResponse(Rate rate);

    @Mapping(target = "author", source = "rateRequest.author", qualifiedByName = "stringToUpperCaseAuthor")
    Rate toRate(RateRequest rateRequest);

    @Mapping(target = "rateList", source = "ratePage", qualifiedByName = "ratePageToRateResponseList")
    @Mapping(target = "currentPageNumber", source = "offset")
    @Mapping(target = "pageLimit", source = "limit")
    @Mapping(target = "totalPages", source = "ratePage.totalPages")
    @Mapping(target = "totalElements", source = "ratePage.totalElements")
    RatePageResponse toResponsePage(Page<Rate> ratePage, int offset, int limit);

    RateAverageResponse toRateAverageResponse(Long personId, BigDecimal averageValue);

    @Mapping(target = "author", source = "author", qualifiedByName = "stringToUpperCaseAuthor")
    void updateRateFromDto(RateRequest rateRequest, @MappingTarget Rate rate);

    @Named("ratePageToRateResponseList")
    List<RateResponse> ratePageToRateResponseList(Page<Rate> ratePage);

    @Named("authorToLowerCaseString")
    default String authorToLowerCaseString(AuthorType authorType) {
        return authorType.name().toLowerCase();
    }

    @Named("stringToUpperCaseAuthor")
    default AuthorType stringToUpperCaseAuthor(String author) {
        return AuthorType.valueOf(author.toUpperCase());
    }

}
