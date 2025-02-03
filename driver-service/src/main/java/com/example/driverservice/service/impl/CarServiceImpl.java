package com.example.driverservice.service.impl;

import com.example.driverservice.dto.car.CarRequest;
import com.example.driverservice.dto.car.CarResponse;
import com.example.driverservice.dto.car.CarPageResponse;
import com.example.driverservice.exception.DbModificationAttemptException;
import com.example.driverservice.exception.ResourceNotFoundException;
import com.example.driverservice.mapper.CarMapper;
import com.example.driverservice.model.Car;
import com.example.driverservice.repository.CarRepository;
import com.example.driverservice.service.CarService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    private final CarMapper carMapper;
    private final MessageSource messageSource;

    @Override
    public CarResponse findById(@Positive(message = "{validate.method.parameter.id.negative}") Long id) {
        Car car = carRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(messageSource
                .getMessage("exception.car.not.found", new Object[] {id}, LocaleContextHolder.getLocale())));
        return carMapper.toResponse(car);
    }

    @Override
    public CarPageResponse findAll(@Min(0) Integer offset, @Min(1) @Max(50) Integer limit) {
        Page<Car> carPage = carRepository.findAll(PageRequest.of(offset, limit));
        return carMapper.toResponsePage(carPage, offset, limit);
    }

    @Override
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
            carMapper.updateCarFromDto(carRequest, car);
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
