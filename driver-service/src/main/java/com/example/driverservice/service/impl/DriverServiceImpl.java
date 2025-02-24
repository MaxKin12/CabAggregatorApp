package com.example.driverservice.service.impl;

import static com.example.driverservice.utility.constants.InternationalizationExceptionVariablesConstants.DRIVER_NOT_FOUND;
import static com.example.driverservice.utility.constants.InternationalizationExceptionVariablesConstants.INVALID_ATTEMPT_CHANGE_DRIVER;

import com.example.driverservice.configuration.properties.DriverServiceProperties;
import com.example.driverservice.dto.common.PageResponse;
import com.example.driverservice.dto.driver.DriverRequest;
import com.example.driverservice.dto.driver.DriverResponse;
import com.example.driverservice.exception.custom.DbModificationAttemptException;
import com.example.driverservice.exception.custom.ResourceNotFoundException;
import com.example.driverservice.mapper.driver.DriverMapper;
import com.example.driverservice.mapper.driver.DriverPageMapper;
import com.example.driverservice.model.entity.Driver;
import com.example.driverservice.repository.DriverRepository;
import com.example.driverservice.service.DriverService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
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
@EnableConfigurationProperties(DriverServiceProperties.class)
public class DriverServiceImpl implements DriverService {

    private final DriverServiceProperties driverServiceProperties;

    private final DriverRepository driverRepository;

    private final DriverMapper driverMapper;

    private final DriverPageMapper driverPageMapper;

    private final MessageSource messageSource;

    @Override
    @Transactional(readOnly = true)
    public DriverResponse findById(@Positive(message = "{validate.method.parameter.id.negative}") Long id) {
        Driver driver = findByIdOrThrow(id);
        return driverMapper.toResponse(driver);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<DriverResponse> findAll(@Min(0) Integer offset, @Min(1) Integer limit) {
        limit = limit < driverServiceProperties.maxPageLimit() ? limit : driverServiceProperties.maxPageLimit();
        Page<Driver> driverPage = driverRepository.findAll(PageRequest.of(offset, limit));
        return driverPageMapper.toResponsePage(driverPage, offset, limit);
    }

    @Override
    @Transactional
    public DriverResponse create(@Valid DriverRequest driverRequest) {
        try {
            Driver driver = driverRepository.save(driverMapper.toDriver(driverRequest));
            return driverMapper.toResponse(driver);
        } catch (Exception e) {
            throw new DbModificationAttemptException(
                    getExceptionMessage(INVALID_ATTEMPT_CHANGE_DRIVER, "create", e.getMessage())
            );
        }
    }

    @Override
    @Transactional
    public DriverResponse update(@Valid DriverRequest driverRequest,
                                 @Positive(message = "{validate.method.parameter.id.negative}") Long id) {
        Driver driver = findByIdOrThrow(id);
        try {
            driverMapper.updateDriverFromDto(driverRequest, driver);
            DriverResponse driverResponse = driverMapper.toResponse(driver);
            driverRepository.flush();
            return driverResponse;
        } catch (Exception e) {
            throw new DbModificationAttemptException(
                    getExceptionMessage(INVALID_ATTEMPT_CHANGE_DRIVER, "update", e.getMessage())
            );
        }
    }

    @Override
    @Transactional
    public void delete(@Positive(message = "{validate.method.parameter.id.negative}") Long id) {
        findByIdOrThrow(id);
        try {
            driverRepository.deleteById(id);
        } catch (Exception e) {
            throw new DbModificationAttemptException(
                    getExceptionMessage(INVALID_ATTEMPT_CHANGE_DRIVER, "delete", e.getMessage())
            );
        }
    }

    private Driver findByIdOrThrow(Long id) {
        return driverRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(getExceptionMessage(DRIVER_NOT_FOUND, id)));
    }

    private String getExceptionMessage(String messageKey, Object... args) {
        return messageSource.getMessage(messageKey, args, LocaleContextHolder.getLocale());
    }

}
