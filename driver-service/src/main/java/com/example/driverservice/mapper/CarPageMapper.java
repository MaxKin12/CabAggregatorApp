package com.example.driverservice.mapper;

import com.example.driverservice.dto.car.CarPageResponse;
import com.example.driverservice.dto.car.CarResponse;
import com.example.driverservice.model.Car;
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
        uses = CarMapper.class
)
public interface CarPageMapper {

    @Mapping(target = "carList", source = "carPage", qualifiedByName = "carPageToCarResponseList")
    @Mapping(target = "currentPageNumber", source = "offset")
    @Mapping(target = "pageLimit", source = "limit")
    @Mapping(target = "totalPages", source = "carPage.totalPages")
    @Mapping(target = "totalElements", source = "carPage.totalElements")
    CarPageResponse toResponsePage(Page<Car> carPage, int offset, int limit);

    @Named("carPageToCarResponseList")
    List<CarResponse> carPageToCarResponseList(Page<Car> carPage);

}
