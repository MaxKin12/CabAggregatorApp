package com.example.driverservice.mapper;

import com.example.driverservice.dto.driver.DriverPageResponse;
import com.example.driverservice.dto.driver.DriverRequest;
import com.example.driverservice.dto.driver.DriverResponse;
import com.example.driverservice.model.Car;
import com.example.driverservice.model.Driver;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DriverMapper {
    @Mapping(target = "carIds", source = "cars", qualifiedByName = "carsToCarIds")
    DriverResponse toResponse(Driver driver);

    Driver toDriver(DriverRequest passengerRequest);

    @Mapping(target = "driverList", source = "driverPage", qualifiedByName = "driverPageToDriverResponseList")
    @Mapping(target = "currentPageNumber", source = "offset")
    @Mapping(target = "pageLimit", source = "limit")
    @Mapping(target = "totalPages", source = "driverPage.totalPages")
    @Mapping(target = "totalElements", source = "driverPage.totalElements")
    DriverPageResponse toResponsePage(Page<Driver> driverPage, int offset, int limit);

    @Named("driverPageToDriverResponseList")
    List<DriverResponse> driverPageToDriverResponseList(Page<Driver> driverList);

    @Named("carsToCarIds")
    default List<Long> carsToCarIds(List<Car> cars) {
        if (cars == null)
            return new ArrayList<>();
        return cars.stream().map(Car::getId).toList();
    }
}
