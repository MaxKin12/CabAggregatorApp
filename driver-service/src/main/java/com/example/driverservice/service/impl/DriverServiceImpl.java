package com.example.driverservice.service.impl;

import static com.example.driverservice.utility.constants.InternationalizationValidationPropertyVariablesConstants.ID_NEGATIVE;

import com.example.driverservice.dto.driver.DriverRequest;
import com.example.driverservice.dto.driver.DriverResponse;
import com.example.driverservice.dto.page.PageResponse;
import com.example.driverservice.mapper.driver.DriverMapper;
import com.example.driverservice.mapper.driver.DriverPageMapper;
import com.example.driverservice.model.entity.Driver;
import com.example.driverservice.repository.DriverRepository;
import com.example.driverservice.service.DriverService;
import com.example.driverservice.utility.validation.DriverServiceValidation;
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
public class DriverServiceImpl implements DriverService {

    private final DriverServiceValidation validation;
    private final DriverRepository driverRepository;
    private final DriverMapper driverMapper;
    private final DriverPageMapper driverPageMapper;

    @Override
    @Transactional(readOnly = true)
    public DriverResponse findById(@Positive(message = ID_NEGATIVE) Long id) {
        Driver driver = validation.findByIdOrThrow(id);
        return driverMapper.toResponse(driver);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<DriverResponse> findAll(@Min(0) Integer offset, @Min(1) Integer limit) {
        limit = validation.cutDownLimit(limit);
        Page<Driver> driverPage = driverRepository.findAll(PageRequest.of(offset, limit));
        return driverPageMapper.toResponsePage(driverPage, offset, limit);
    }

    @Override
    @Transactional
    public DriverResponse create(@Valid DriverRequest driverRequest) {
        Driver driver = driverMapper.toDriver(driverRequest);
        Driver savedDriver = validation.saveOrThrow(driver);
        return driverMapper.toResponse(savedDriver);
    }

    @Override
    @Transactional
    public DriverResponse update(@Valid DriverRequest driverRequest,
                                 @Positive(message = ID_NEGATIVE) Long id) {
        Driver driver = validation.findByIdOrThrow(id);
        validation.updateOrThrow(driver, driverRequest);
        return driverMapper.toResponse(driver);
    }

    @Override
    @Transactional
    public void updateRate(RateChangeEventResponse event) {
        Driver driver = findByIdOrThrow(event.recipientId());
        driver.setRate(event.rate());
        driverRepository.save(driver);
    }

    @Override
    @Transactional
    public void delete(@Positive(message = ID_NEGATIVE) Long id) {
        validation.findByIdOrThrow(id);
        driverRepository.deleteById(id);
    }

}
