package com.example.ridesservice.mapper;

import com.example.ridesservice.dto.ride.response.RidePageResponse;
import com.example.ridesservice.dto.ride.response.RideResponse;
import com.example.ridesservice.model.Ride;
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
        uses = RideMapper.class
)
public interface RidePageMapper {

    @Mapping(target = "rideList", source = "ridePage", qualifiedByName = "ridePageToRideResponseList")
    @Mapping(target = "currentPageNumber", source = "offset")
    @Mapping(target = "pageLimit", source = "limit")
    @Mapping(target = "totalPages", source = "ridePage.totalPages")
    @Mapping(target = "totalElements", source = "ridePage.totalElements")
    RidePageResponse toResponsePage(Page<Ride> ridePage, int offset, int limit);

    @Named("ridePageToRideResponseList")
    List<RideResponse> ridePageToRideResponseList(Page<Ride> ridePage);

}
