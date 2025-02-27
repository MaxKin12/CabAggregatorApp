package com.example.ratesservice.mapper.rate;

import com.example.ratesservice.dto.rate.RateRequest;
import com.example.ratesservice.dto.rate.RateResponse;
import com.example.ratesservice.enums.RecipientType;
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

    @Mapping(target = "recipient", source = "recipient", qualifiedByName = "recipientToLowerCaseString")
    RateResponse toResponse(Rate rate);

    @Mapping(target = "recipient", source = "rateRequest.recipient", qualifiedByName = "stringToUpperCaseRecipient")
    Rate toRate(RateRequest rateRequest);

    @Mapping(target = "recipient", source = "recipient", qualifiedByName = "stringToUpperCaseRecipient")
    void updateRateFromDto(RateRequest rateRequest, @MappingTarget Rate rate);

    @Named("recipientToLowerCaseString")
    default String recipientToLowerCaseString(RecipientType recipientType) {
        return recipientType == null ? null : recipientType.name().toLowerCase();
    }

    @Named("stringToUpperCaseRecipient")
    default RecipientType stringToUpperCaseRecipient(String author) {
        return author == null ? null : RecipientType.valueOf(author.toUpperCase());
    }

}
