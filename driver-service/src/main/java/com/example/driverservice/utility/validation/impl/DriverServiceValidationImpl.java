package com.example.driverservice.utility.validation.impl;

import static com.example.driverservice.utility.constants.InternationalizationExceptionVariablesConstants.DRIVER_NOT_FOUND;
import static com.example.driverservice.utility.constants.InternationalizationExceptionVariablesConstants.INVALID_ATTEMPT_CHANGE_DRIVER;

import com.example.driverservice.configuration.properties.DriverServiceProperties;
import com.example.driverservice.dto.driver.DriverRequest;
import com.example.driverservice.exception.custom.DbModificationAttemptException;
import com.example.driverservice.exception.custom.EntityNotFoundException;
import com.example.driverservice.mapper.driver.DriverMapper;
import com.example.driverservice.model.entity.Driver;
import com.example.driverservice.repository.DriverRepository;
import com.example.driverservice.utility.validation.DriverServiceValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DriverServiceValidationImpl implements DriverServiceValidation {

    private final DriverServiceProperties properties;
    private final DriverRepository driverRepository;
    private final DriverMapper driverMapper;

    @Override
    public Driver findByIdOrThrow(Long id) {
        return driverRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(DRIVER_NOT_FOUND, id.toString()));
    }

    @Override
    public int cutDownLimit(int limit) {
        return limit < properties.maxPageLimit() ? limit : properties.maxPageLimit();
    }

    @Override
    public Driver saveOrThrow(Driver driver) {
        try {
            return driverRepository.save(driver);
        } catch (Exception e) {
            throw new DbModificationAttemptException(INVALID_ATTEMPT_CHANGE_DRIVER, "create", e.getMessage());
        }
    }

    @Override
    public void updateOrThrow(Driver driver, DriverRequest driverRequest) {
        try {
            driverMapper.updateDriverFromDto(driverRequest, driver);
            driverRepository.flush();
        } catch (Exception e) {
            throw new DbModificationAttemptException(INVALID_ATTEMPT_CHANGE_DRIVER, "update", e.getMessage());
        }
    }

}
