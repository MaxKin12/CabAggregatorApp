package com.example.driverservice.mapper;

import com.example.driverservice.dto.driver.DriverPageResponse;
import com.example.driverservice.dto.driver.DriverRequest;
import com.example.driverservice.dto.driver.DriverResponse;
import com.example.driverservice.enums.Sex;
import com.example.driverservice.model.Car;
import com.example.driverservice.model.Driver;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DriverMapper {
    @Mapping(target = "carIds", source = "cars", qualifiedByName = "carsToCarIds")
    @Mapping(target = "sex", ignore = true)
    DriverResponse toResponse(Driver driver);

    @Mapping(target = "sex", ignore = true)
    Driver toDriver(DriverRequest driverRequest);

    @Mapping(target = "driverList", source = "driverPage", qualifiedByName = "driverPageToDriverResponseList")
    @Mapping(target = "currentPageNumber", source = "offset")
    @Mapping(target = "pageLimit", source = "limit")
    @Mapping(target = "totalPages", source = "driverPage.totalPages")
    @Mapping(target = "totalElements", source = "driverPage.totalElements")
    DriverPageResponse toResponsePage(Page<Driver> driverPage, int offset, int limit);

    @Mapping(target = "sex", ignore = true)
    void updateDriverFromDto(DriverRequest driverRequest, @MappingTarget Driver driver);

    @Named("driverPageToDriverResponseList")
    List<DriverResponse> driverPageToDriverResponseList(Page<Driver> driverPage);

    @Named("carsToCarIds")
    default List<Long> carsToCarIds(List<Car> carList) {
        if (carList == null)
            return new ArrayList<>();
        return carList.stream().map(Car::getId).toList();
    }

    @AfterMapping
    default void setSexField(DriverRequest driverRequest, @MappingTarget Driver driver) {
        driver.setSex(Sex.valueOf(driverRequest.sex().toUpperCase()));
    }

    @AfterMapping
    default void setSexField(Driver driver, @MappingTarget DriverResponse.DriverResponseBuilder driverResponse) {
        driverResponse.sex(driver.getSex().name().toLowerCase());
    }
}
