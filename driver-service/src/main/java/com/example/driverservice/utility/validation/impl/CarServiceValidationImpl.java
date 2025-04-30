package com.example.driverservice.utility.validation.impl;

import static com.example.driverservice.utility.constants.InternationalizationExceptionPropertyVariablesConstants.CAR_NOT_FOUND;
import static com.example.driverservice.utility.constants.InternationalizationExceptionPropertyVariablesConstants.INVALID_ATTEMPT_CHANGE_CAR;

import com.example.driverservice.configuration.properties.DriverServiceProperties;
import com.example.driverservice.dto.car.CarRequest;
import com.example.driverservice.exception.custom.DbModificationAttemptException;
import com.example.driverservice.exception.custom.EntityNotFoundException;
import com.example.driverservice.mapper.car.CarMapper;
import com.example.driverservice.model.entity.Car;
import com.example.driverservice.repository.CarRepository;
import com.example.driverservice.utility.validation.CarServiceValidation;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CarServiceValidationImpl implements CarServiceValidation {

    private final DriverServiceProperties properties;
    private final CarRepository carRepository;
    private final CarMapper carMapper;

    @Override
    public Car findByIdOrThrow(UUID id) {
        return carRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(CAR_NOT_FOUND, id.toString()));
    }

    @Override
    public int cutDownLimit(int limit) {
        return limit < properties.maxPageLimit() ? limit : properties.maxPageLimit();
    }

    @Override
    public Car saveOrThrow(Car car) {
        try {
            return carRepository.save(car);
        } catch (Exception e) {
            throw new DbModificationAttemptException(INVALID_ATTEMPT_CHANGE_CAR, "create", e.getMessage());
        }
    }

    @Override
    public void updateOrThrow(Car car, CarRequest carRequest) {
        try {
            carMapper.updateCarFromDto(carRequest, car);
            carRepository.flush();
        } catch (Exception e) {
            throw new DbModificationAttemptException(INVALID_ATTEMPT_CHANGE_CAR, "update", e.getMessage());
        }
    }

}
