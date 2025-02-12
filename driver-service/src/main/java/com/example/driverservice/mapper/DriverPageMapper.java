package com.example.driverservice.mapper;

import com.example.driverservice.dto.driver.DriverPageResponse;
import com.example.driverservice.dto.driver.DriverResponse;
import com.example.driverservice.model.Driver;
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
        uses = DriverMapper.class
)
public interface DriverPageMapper {

    @Mapping(target = "driverList", source = "driverPage", qualifiedByName = "driverPageToDriverResponseList")
    @Mapping(target = "currentPageNumber", source = "offset")
    @Mapping(target = "pageLimit", source = "limit")
    @Mapping(target = "totalPages", source = "driverPage.totalPages")
    @Mapping(target = "totalElements", source = "driverPage.totalElements")
    DriverPageResponse toResponsePage(Page<Driver> driverPage, int offset, int limit);

    @Named("driverPageToDriverResponseList")
    List<DriverResponse> driverPageToDriverResponseList(Page<Driver> driverPage);

}
