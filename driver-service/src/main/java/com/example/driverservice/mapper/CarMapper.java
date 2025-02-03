package com.example.driverservice.mapper;

import com.example.driverservice.dto.car.CarRequest;
import com.example.driverservice.dto.car.CarResponse;
import com.example.driverservice.dto.car.CarPageResponse;
import com.example.driverservice.model.Car;
import com.example.driverservice.model.Driver;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CarMapper {
    @Mapping(target = "driverId", source = "driver.id")
    CarResponse toResponse(Car car);

    @Mapping(target = "driver", source = "driverId", qualifiedByName = "driverIdToDriver")
    Car toCar(CarRequest passengerRequest);

    @Mapping(target = "carList", source = "carPage", qualifiedByName = "carPageToCarResponseList")
    @Mapping(target = "currentPageNumber", source = "offset")
    @Mapping(target = "pageLimit", source = "limit")
    @Mapping(target = "totalPages", source = "carPage.totalPages")
    @Mapping(target = "totalElements", source = "carPage.totalElements")
    CarPageResponse toResponsePage(Page<Car> carPage, int offset, int limit);

    void updateCarFromDto(CarRequest carRequest, @MappingTarget Car car);

    @Named("carPageToCarResponseList")
    List<CarResponse> carPageToCarResponseList(Page<Car> carPage);

    @Named("driverIdToDriver")
    default Driver driverIdToDriver(Long id) {
        Driver driver = new Driver();
        driver.setId(id);
        return driver;
    }
}

