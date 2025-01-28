package com.example.driverservice.service.impl;

import com.example.driverservice.dto.driver.DriverResponseList;
import com.example.driverservice.dto.driver.DriverRequest;
import com.example.driverservice.dto.driver.DriverResponse;
import com.example.driverservice.exception.DBModificationAttemptException;
import com.example.driverservice.exception.ResourceNotFoundException;
import com.example.driverservice.mapper.DriverMapper;
import com.example.driverservice.model.Driver;
import com.example.driverservice.repository.DriverRepository;
import com.example.driverservice.service.DriverService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import static com.example.driverservice.constant.ExceptionMessagesConstants.DRIVER_NOT_FOUND_MESSAGE;
import static com.example.driverservice.constant.ExceptionMessagesConstants.INVALID_ATTEMPT_MESSAGE;
import static com.example.driverservice.constant.ExceptionMessagesConstants.NEGATIVE_ID_MESSAGE;

@Service
@Validated
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {
    private final DriverRepository driverRepository;
    private final DriverMapper driverMapper;

    public DriverResponse findById(@Positive(message = NEGATIVE_ID_MESSAGE) Long id) {
        Driver driver = driverRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(DRIVER_NOT_FOUND_MESSAGE.formatted(id)));
        return driverMapper.toResponse(driver);
    }

    public DriverResponseList findAll() {
        List<Driver> drivers = driverRepository.findAll();
        return driverMapper.toResponseList(drivers);
    }

    @Transactional
    public DriverResponse create(@Valid DriverRequest driverRequest) {
        try {
            Driver driver = driverRepository.save(driverMapper.toDriver(driverRequest));
            return driverMapper.toResponse(driver);
        } catch (Exception e) {
            throw new DBModificationAttemptException(INVALID_ATTEMPT_MESSAGE.formatted("create", e.getMessage()));
        }
    }

    @Transactional
    public DriverResponse update(@Valid DriverRequest driverRequest,
                                    @Positive(message = NEGATIVE_ID_MESSAGE) Long id) {
        Driver driver = driverRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(DRIVER_NOT_FOUND_MESSAGE.formatted(id)));
        try {
            Driver editedPassenger = driverMapper.toDriver(driverRequest);
            driver.setName(editedPassenger.getName());
            driver.setEmail(editedPassenger.getEmail());
            driver.setPhone(editedPassenger.getPhone());
            DriverResponse driverResponse = driverMapper.toResponse(driver);
            driverRepository.flush();
            return driverResponse;
        } catch (Exception e) {
            throw new DBModificationAttemptException(INVALID_ATTEMPT_MESSAGE.formatted("update", e.getMessage()));
        }
    }

    @Transactional
    public void delete(@Positive(message = NEGATIVE_ID_MESSAGE) Long id) {
        try {
            driverRepository.deleteById(id);
        } catch (Exception e) {
            throw new DBModificationAttemptException(INVALID_ATTEMPT_MESSAGE.formatted("delete", e.getMessage()));
        }
    }
}
