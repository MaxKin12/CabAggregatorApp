package com.example.ratesservice.mapper;

import com.example.ratesservice.dto.RateRequest;
import com.example.ratesservice.dto.RateResponse;
import com.example.ratesservice.enums.AuthorType;
import com.example.ratesservice.model.Rate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface RateMapper {

    @Mapping(target = "author", source = "author", qualifiedByName = "authorToLowerCaseString")
    RateResponse toResponse(Rate rate);

    @Mapping(target = "author", source = "rateRequest.author", qualifiedByName = "stringToUpperCaseAuthor")
    Rate toRate(RateRequest rateRequest);

    @Mapping(target = "author", source = "author", qualifiedByName = "stringToUpperCaseAuthor")
    void updateRateFromDto(RateRequest rateRequest, @MappingTarget Rate rate);

    @Named("authorToLowerCaseString")
    default String authorToLowerCaseString(AuthorType authorType) {
        return authorType.name().toLowerCase();
    }

    @Named("stringToUpperCaseAuthor")
    default AuthorType stringToUpperCaseAuthor(String author) {
        return AuthorType.valueOf(author.toUpperCase());
    }

}
