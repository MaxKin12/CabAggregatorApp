package com.example.driverservice.mapper;

import com.example.driverservice.dto.car.CarRequest;
import com.example.driverservice.dto.car.CarResponse;
import com.example.driverservice.dto.car.CarResponseList;
import com.example.driverservice.model.Car;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class CarMapper {
    public abstract CarResponse toResponse(Car car);
    public abstract Car toCar(CarRequest passengerRequest);

    public CarResponseList toResponseList(List<Car> carList) {
        return new CarResponseList(toList(carList));
    }
    protected abstract List<CarResponse> toList(List<Car> carList);
}

