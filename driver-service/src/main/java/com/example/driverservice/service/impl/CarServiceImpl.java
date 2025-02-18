package com.example.driverservice.service.impl;

import static com.example.driverservice.utility.constants.InternationalizationExceptionVariablesConstants.CAR_NOT_FOUND;
import static com.example.driverservice.utility.constants.InternationalizationExceptionVariablesConstants.INVALID_ATTEMPT_CHANGE_CAR;

import com.example.driverservice.dto.car.CarRequest;
import com.example.driverservice.dto.car.CarResponse;
import com.example.driverservice.dto.common.PageResponse;
import com.example.driverservice.exception.custom.DbModificationAttemptException;
import com.example.driverservice.exception.custom.ResourceNotFoundException;
import com.example.driverservice.mapper.car.CarMapper;
import com.example.driverservice.mapper.car.CarPageMapper;
import com.example.driverservice.model.entity.Car;
import com.example.driverservice.repository.CarRepository;
import com.example.driverservice.service.CarService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final CarMapper carMapper;

    private final CarPageMapper carPageMapper;
    private final MessageSource messageSource;

    @Override
    @Transactional(readOnly = true)
    public CarResponse findById(@Positive(message = "{validate.method.parameter.id.negative}") Long id) {
        Car car = findByIdOrThrow(id);
        return carMapper.toResponse(car);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<CarResponse> findAll(@Min(0) Integer offset, @Min(1) Integer limit) {
        limit = limit < 50 ? limit : 50;
        Page<Car> carPage = carRepository.findAll(PageRequest.of(offset, limit));
        return carPageMapper.toResponsePage(carPage, offset, limit);
    }

    @Override
    @Transactional
    public CarResponse create(@Valid CarRequest carRequest) {
        try {
            Car car = carRepository.save(carMapper.toCar(carRequest));
            return carMapper.toResponse(car);
        } catch (Exception e) {
            throw new DbModificationAttemptException(
                    getExceptionMessage(INVALID_ATTEMPT_CHANGE_CAR, "create", e.getMessage())
            );
        }
    }

    @Override
    @Transactional
    public CarResponse update(@Valid CarRequest carRequest,
                              @Positive(message = "{validate.method.parameter.id.negative}") Long id) {
        Car car = findByIdOrThrow(id);
        try {
            carMapper.updateCarFromDto(carRequest, car);
            CarResponse carResponse = carMapper.toResponse(car);
            carRepository.flush();
            return carResponse;
        } catch (Exception e) {
            throw new DbModificationAttemptException(
                    getExceptionMessage(INVALID_ATTEMPT_CHANGE_CAR, "update", e.getMessage())
            );
        }
    }

    @Override
    @Transactional
    public void delete(@Positive(message = "{validate.method.parameter.id.negative}") Long id) {
        findByIdOrThrow(id);
        try {
            carRepository.deleteById(id);
        } catch (Exception e) {
            throw new DbModificationAttemptException(
                    getExceptionMessage(INVALID_ATTEMPT_CHANGE_CAR, "delete", e.getMessage())
            );
        }
    }

    private Car findByIdOrThrow(Long id) {
        return carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(getExceptionMessage(CAR_NOT_FOUND, id)));
    }

    private String getExceptionMessage(String messageKey, Object... args) {
        return messageSource.getMessage(messageKey, args, LocaleContextHolder.getLocale());
    }

}
