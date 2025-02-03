package com.example.ridesservice.mapper;

import com.example.ridesservice.dto.RidePageResponse;
import com.example.ridesservice.dto.RideRequest;
import com.example.ridesservice.dto.RideResponse;
import com.example.ridesservice.enums.RideStatus;
import com.example.ridesservice.model.Ride;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        imports = RideStatus.class)
public interface RideMapper {
    @Mapping(target = "status", expression = "java(ride.getStatus().name().toLowerCase())")
    RideResponse toResponse(Ride ride);

    @Mapping(target = "status", expression = "java(RideStatus.valueOf(rideRequest.status().toUpperCase()))")
    Ride toRide(RideRequest rideRequest);

    @Mapping(target = "rideList", source = "ridePage", qualifiedByName = "ridePageToRideResponseList")
    @Mapping(target = "currentPageNumber", source = "offset")
    @Mapping(target = "pageLimit", source = "limit")
    @Mapping(target = "totalPages", source = "ridePage.totalPages")
    @Mapping(target = "totalElements", source = "ridePage.totalElements")
    RidePageResponse toResponsePage(Page<Ride> ridePage, int offset, int limit);

    @Mapping(target = "status", expression = "java(RideStatus.valueOf(rideRequest.status().toUpperCase()))")
    void updateRideFromDto(RideRequest rideRequest, @MappingTarget Ride ride);

    @Named("ridePageToRideResponseList")
    List<RideResponse> ridePageToRideResponseList(Page<Ride> ridePage);
}
