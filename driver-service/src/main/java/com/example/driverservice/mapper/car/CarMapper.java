package com.example.driverservice.mapper.car;

import com.example.driverservice.dto.car.CarRequest;
import com.example.driverservice.dto.car.CarResponse;
import com.example.driverservice.model.entity.Car;
import com.example.driverservice.model.entity.Driver;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.UUID;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CarMapper {

    @Mapping(target = "driverId", source = "driver.id")
    CarResponse toResponse(Car car);

    @Mapping(target = "driver", source = "driverId", qualifiedByName = "driverIdToDriver")
    Car toCar(CarRequest carRequest);

    void updateCarFromDto(CarRequest carRequest, @MappingTarget Car car);

    @Named("driverIdToDriver")
    default Driver driverIdToDriver(UUID id) {
        Driver driver = new Driver();
        driver.setId(id);
        return driver;
    }

}

