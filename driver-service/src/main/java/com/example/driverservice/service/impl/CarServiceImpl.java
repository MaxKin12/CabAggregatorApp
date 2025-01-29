package com.example.driverservice.service.impl;

import com.example.driverservice.dto.car.CarRequest;
import com.example.driverservice.dto.car.CarResponse;
import com.example.driverservice.dto.car.CarResponseList;
import com.example.driverservice.exception.DbModificationAttemptException;
import com.example.driverservice.exception.ResourceNotFoundException;
import com.example.driverservice.mapper.CarMapper;
import com.example.driverservice.model.Car;
import com.example.driverservice.repository.CarRepository;
import com.example.driverservice.service.CarService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    private final CarMapper carMapper;
    private final MessageSource messageSource;

    @Override
    @Transactional
    public CarResponse findById(@Positive(message = "{validate.method.parameter.id.negative}") Long id) {
        Car car = carRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(messageSource
                .getMessage("exception.car.not.found", new Object[] {id}, LocaleContextHolder.getLocale())));
        return carMapper.toResponse(car);
    }

    @Override
    @Transactional
    public CarResponseList findAll() {
        List<Car> cars = carRepository.findAll();
        return carMapper.toResponseList(cars);
    }

    @Override
    @Transactional
    public CarResponse create(@Valid CarRequest carRequest) {
        try {
            Car car = carRepository.save(carMapper.toCar(carRequest));
            return carMapper.toResponse(car);
        } catch (Exception e) {
            throw new DbModificationAttemptException(messageSource
                    .getMessage("exception.invalid.attempt.change.car", new Object[] {"create", e.getMessage()},
                            LocaleContextHolder.getLocale()));
        }
    }

    @Override
    @Transactional
    public CarResponse update(@Valid CarRequest carRequest,
                              @Positive(message = "{validate.method.parameter.id.negative}") Long id) {
        Car car = carRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(messageSource
                .getMessage("exception.car.not.found", new Object[] {id}, LocaleContextHolder.getLocale())));
        try {
            Car editedCar = carMapper.toCar(carRequest);
            car.setBrand(editedCar.getBrand());
            car.setNumber(editedCar.getNumber());
            car.setColor(editedCar.getColor());
            CarResponse carResponse = carMapper.toResponse(car);
            carRepository.flush();
            return carResponse;
        } catch (Exception e) {
            throw new DbModificationAttemptException(messageSource
                    .getMessage("exception.invalid.attempt.change.car", new Object[] {"update", e.getMessage()},
                            LocaleContextHolder.getLocale()));
        }
    }

    @Override
    @Transactional
    public void delete(@Positive(message = "{validate.method.parameter.id.negative}") Long id) {
        try {
            carRepository.deleteById(id);
        } catch (Exception e) {
            throw new DbModificationAttemptException(messageSource
                    .getMessage("exception.invalid.attempt.change.car", new Object[] {"delete", e.getMessage()},
                            LocaleContextHolder.getLocale()));
        }
    }
}
