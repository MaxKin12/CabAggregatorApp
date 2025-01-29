package com.example.driverservice.mapper;

import com.example.driverservice.dto.car.CarRequest;
import com.example.driverservice.dto.car.CarResponse;
import com.example.driverservice.dto.car.CarResponseList;
import com.example.driverservice.model.Car;
import com.example.driverservice.model.Driver;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class CarMapper {
    @Mapping(target = "driverId", source = "driver", qualifiedByName = "driverToDriverId")
    public abstract CarResponse toResponse(Car car);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleteAt", ignore = true)
    @Mapping(target = "driver", source = "driverId", qualifiedByName = "driverIdToDriver")
    public abstract Car toCar(CarRequest passengerRequest);

    public CarResponseList toResponseList(List<Car> carList) {
        return new CarResponseList(toList(carList));
    }

    @Named("driverToDriverId")
    protected Long driverToDriverId(Driver driver) {
        if (driver == null)
            return null;
        return driver.getId();
    }

    @Named("driverIdToDriver")
    protected Driver driverIdToDriver(Long id) {
        Driver driver = new Driver();
        driver.setId(id);
        return driver;
    }

    protected abstract List<CarResponse> toList(List<Car> carList);
}

