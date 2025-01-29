package com.example.driverservice.service.impl;

import com.example.driverservice.dto.driver.DriverResponseList;
import com.example.driverservice.dto.driver.DriverRequest;
import com.example.driverservice.dto.driver.DriverResponse;
import com.example.driverservice.exception.DbModificationAttemptException;
import com.example.driverservice.exception.ResourceNotFoundException;
import com.example.driverservice.mapper.DriverMapper;
import com.example.driverservice.model.Driver;
import com.example.driverservice.repository.DriverRepository;
import com.example.driverservice.service.DriverService;
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
public class DriverServiceImpl implements DriverService {
    private final DriverRepository driverRepository;
    private final DriverMapper driverMapper;
    private final MessageSource messageSource;

    @Override
    public DriverResponse findById(@Positive(message = "{validate.method.parameter.id.negative}") Long id) {
        Driver driver = driverRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(messageSource
                .getMessage("exception.driver.not.found", new Object[] {id}, LocaleContextHolder.getLocale())));
        return driverMapper.toResponse(driver);
    }

    @Override
    public DriverResponseList findAll() {
        List<Driver> drivers = driverRepository.findAll();
        return driverMapper.toResponseList(drivers);
    }

    @Override
    public DriverResponse create(@Valid DriverRequest driverRequest) {
        try {
            Driver driver = driverRepository.save(driverMapper.toDriver(driverRequest));
            return driverMapper.toResponse(driver);
        } catch (Exception e) {
            throw new DbModificationAttemptException(messageSource
                    .getMessage("exception.invalid.attempt.change.driver", new Object[] {"create", e.getMessage()},
                            LocaleContextHolder.getLocale()));
        }
    }

    @Override
    @Transactional
    public DriverResponse update(@Valid DriverRequest driverRequest,
                                 @Positive(message = "{validate.method.parameter.id.negative}") Long id) {
        Driver driver = driverRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(messageSource
                .getMessage("exception.driver.not.found", new Object[] {id}, LocaleContextHolder.getLocale())));
        try {
            Driver editedDriver = driverMapper.toDriver(driverRequest);
            driver.setName(editedDriver.getName());
            driver.setEmail(editedDriver.getEmail());
            driver.setPhone(editedDriver.getPhone());
            driver.setCar(editedDriver.getCar());
            DriverResponse driverResponse = driverMapper.toResponse(driver);
            driverRepository.flush();
            return driverResponse;
        } catch (Exception e) {
            throw new DbModificationAttemptException(messageSource
                    .getMessage("exception.invalid.attempt.change.driver", new Object[] {"update", e.getMessage()},
                            LocaleContextHolder.getLocale()));
        }
    }

    @Override
    public void delete(@Positive(message = "{validate.method.parameter.id.negative}") Long id) {
        try {
            driverRepository.deleteById(id);
        } catch (Exception e) {
            throw new DbModificationAttemptException(messageSource
                    .getMessage("exception.invalid.attempt.change.driver", new Object[] {"delete", e.getMessage()},
                            LocaleContextHolder.getLocale()));
        }
    }
}
