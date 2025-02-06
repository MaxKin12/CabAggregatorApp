package com.example.passengerservice.mapper;

import com.example.passengerservice.dto.PassengerPageResponse;
import com.example.passengerservice.dto.PassengerRequest;
import com.example.passengerservice.dto.PassengerResponse;
import com.example.passengerservice.model.Passenger;
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
public interface PassengerMapper {

    PassengerResponse toResponse(Passenger passenger);

    Passenger toPassenger(PassengerRequest passengerRequest);

    @Mapping(target = "passengerList", source = "passengerPage",
             qualifiedByName = "passengerPageToPassengerResponseList")
    @Mapping(target = "currentPageNumber", source = "offset")
    @Mapping(target = "pageLimit", source = "limit")
    @Mapping(target = "totalPages", source = "passengerPage.totalPages")
    @Mapping(target = "totalElements", source = "passengerPage.totalElements")
    PassengerPageResponse toResponsePage(Page<Passenger> passengerPage, int offset, int limit);

    void updatePassengerFromDto(PassengerRequest passengerRequest, @MappingTarget Passenger passenger);

    @Named("passengerPageToPassengerResponseList")
    List<PassengerResponse> passengerPageToPassengerResponseList(Page<Passenger> passengerPage);

}
