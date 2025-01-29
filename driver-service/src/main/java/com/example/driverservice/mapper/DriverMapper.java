package com.example.driverservice.mapper;

import com.example.driverservice.dto.driver.DriverResponseList;
import com.example.driverservice.dto.driver.DriverRequest;
import com.example.driverservice.dto.driver.DriverResponse;
import com.example.driverservice.model.Car;
import com.example.driverservice.model.Driver;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class DriverMapper {
    @Mapping(target = "carIds", source = "cars", qualifiedByName = "carsToCarIds")
    public abstract DriverResponse toResponse(Driver driver);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cars", ignore = true)
    @Mapping(target = "deleteAt", ignore = true)
    public abstract Driver toDriver(DriverRequest passengerRequest);

    public DriverResponseList toResponseList(List<Driver> driverList) {
        return new DriverResponseList(toList(driverList));
    }

    @Named("carsToCarIds")
    protected List<Long> carsToCarIds(List<Car> cars) {
        if (cars == null)
            return new ArrayList<>();
        return cars.stream().map(Car::getId).collect(Collectors.toList());
    }

    protected abstract List<DriverResponse> toList(List<Driver> driverList);
}
