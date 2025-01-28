package com.example.driverservice.mapper;

import com.example.driverservice.dto.driver.DriverResponseList;
import com.example.driverservice.dto.driver.DriverRequest;
import com.example.driverservice.dto.driver.DriverResponse;
import com.example.driverservice.model.Driver;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class DriverMapper {
    public abstract DriverResponse toResponse(Driver driver);
    public abstract Driver toDriver(DriverRequest passengerRequest);

    public DriverResponseList toResponseList(List<Driver> driverList) {
        return new DriverResponseList(toList(driverList));
    }
    protected abstract List<DriverResponse> toList(List<Driver> driverList);
}
