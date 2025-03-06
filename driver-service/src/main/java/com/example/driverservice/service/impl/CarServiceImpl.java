package com.example.driverservice.service.impl;

import static com.example.driverservice.utility.constants.InternationalizationValidationPropertyVariablesConstants.ID_NEGATIVE;

import com.example.driverservice.dto.car.CarRequest;
import com.example.driverservice.dto.car.CarResponse;
import com.example.driverservice.dto.page.PageResponse;
import com.example.driverservice.mapper.car.CarMapper;
import com.example.driverservice.mapper.car.CarPageMapper;
import com.example.driverservice.model.entity.Car;
import com.example.driverservice.repository.CarRepository;
import com.example.driverservice.service.CarService;
import com.example.driverservice.utility.validation.CarServiceValidation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarServiceValidation validation;
    private final CarRepository carRepository;
    private final CarMapper carMapper;
    private final CarPageMapper carPageMapper;

    @Override
    @Transactional(readOnly = true)
    public CarResponse findById(@Positive(message = ID_NEGATIVE) Long id) {
        Car car = validation.findByIdOrThrow(id);
        return carMapper.toResponse(car);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse findAll(@Min(0) Integer offset, @Min(1) Integer limit) {
        limit = validation.cutDownLimit(limit);
        Page<Car> carPage = carRepository.findAll(PageRequest.of(offset, limit));
        return carPageMapper.toResponsePage(carPage, offset, limit);
    }

    @Override
    @Transactional
    public CarResponse create(@Valid CarRequest carRequest) {
        Car car = carMapper.toCar(carRequest);
        Car savedCar = validation.saveOrThrow(car);
        return carMapper.toResponse(savedCar);
    }

    @Override
    @Transactional
    public CarResponse update(@Valid CarRequest carRequest,
                              @Positive(message = ID_NEGATIVE) Long id) {
        Car car = validation.findByIdOrThrow(id);
        validation.updateOrThrow(car, carRequest);
        return carMapper.toResponse(car);
    }

    @Override
    @Transactional
    public void delete(@Positive(message = ID_NEGATIVE) Long id) {
        validation.findByIdOrThrow(id);
        carRepository.deleteById(id);
    }

}
