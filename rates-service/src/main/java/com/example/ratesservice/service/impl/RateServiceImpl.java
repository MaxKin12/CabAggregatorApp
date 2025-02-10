package com.example.ratesservice.service.impl;

import static com.example.ratesservice.utility.constants.InternationalizationExceptionVariablesConstants.INVALID_ATTEMPT_CHANGE_RATE;
import static com.example.ratesservice.utility.constants.InternationalizationExceptionVariablesConstants.RATE_ALREADY_EXISTS;
import static com.example.ratesservice.utility.constants.InternationalizationExceptionVariablesConstants.RATE_DRIVER_LIST_IS_EMPTY;
import static com.example.ratesservice.utility.constants.InternationalizationExceptionVariablesConstants.RATE_NOT_FOUND;
import static com.example.ratesservice.utility.constants.InternationalizationExceptionVariablesConstants.RATE_PASSENGER_LIST_IS_EMPTY;

import com.example.ratesservice.configuration.RateServiceProperties;
import com.example.ratesservice.dto.RateAverageResponse;
import com.example.ratesservice.dto.RatePageResponse;
import com.example.ratesservice.dto.RateRequest;
import com.example.ratesservice.dto.RateResponse;
import com.example.ratesservice.enums.AuthorType;
import com.example.ratesservice.exception.custom.DbModificationAttemptException;
import com.example.ratesservice.exception.custom.RateAlreadyExistsException;
import com.example.ratesservice.exception.custom.RateListIsEmptyException;
import com.example.ratesservice.exception.custom.RateNotFoundException;
import com.example.ratesservice.mapper.RateMapper;
import com.example.ratesservice.model.Rate;
import com.example.ratesservice.repository.RateRepository;
import com.example.ratesservice.service.RateService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
@EnableConfigurationProperties(RateServiceProperties.class)
public class RateServiceImpl implements RateService {

    private final RateServiceProperties rateServiceProperties;

    private final RateRepository rateRepository;

    private final RateMapper rateMapper;

    private final MessageSource messageSource;

    @Override
    public RateResponse findById(@Positive(message = "{validate.method.parameter.id.negative}") Long id) {
        Rate rate = findByIdOrThrow(id);
        return rateMapper.toResponse(rate);
    }

    @Override
    public RatePageResponse findAll(@Min(0) Integer offset, @Min(1) @Max(50) Integer limit) {
        Page<Rate> ratePage = rateRepository.findAll(PageRequest.of(offset, limit));
        return rateMapper.toResponsePage(ratePage, offset, limit);
    }

    @Override
    public RateAverageResponse findAveragePassengerRate(
            @Positive(message = "{validate.method.parameter.id.negative}") Long passengerId
    ) {
        List<Rate> ratePage = rateRepository.findByPassengerIdAndAuthor(
                PageRequest.of(0, rateServiceProperties.lastRidesCount(), Sort.by(Sort.Order.desc("id"))),
                passengerId,
                AuthorType.DRIVER);
        double average = ratePage
                .stream()
                .mapToDouble(Rate::getValue)
                .average()
                .orElseThrow(() -> new RateListIsEmptyException(
                        getRateListIsEmptyExceptionMessage(RATE_PASSENGER_LIST_IS_EMPTY, passengerId)));
        BigDecimal averageDecimal = BigDecimal.valueOf(average).setScale(2, RoundingMode.CEILING);
        return rateMapper.toRateAverageResponse(passengerId, averageDecimal);
    }

    @Override
    public RateAverageResponse findAverageDriverRate(
            @Positive(message = "{validate.method.parameter.id.negative}") Long driverId
    ) {
        List<Rate> ratePage = rateRepository.findByDriverIdAndAuthor(
                PageRequest.of(0, rateServiceProperties.lastRidesCount(), Sort.by(Sort.Order.desc("id"))),
                driverId,
                AuthorType.PASSENGER);
        double average = ratePage
                .stream()
                .mapToDouble(Rate::getValue)
                .average()
                .orElseThrow(() -> new RateListIsEmptyException(
                        getRateListIsEmptyExceptionMessage(RATE_DRIVER_LIST_IS_EMPTY, driverId)));
        BigDecimal averageDecimal = BigDecimal.valueOf(average).setScale(2, RoundingMode.CEILING);
        return rateMapper.toRateAverageResponse(driverId, averageDecimal);
    }

    @Override
    public RateResponse create(@Valid RateRequest rateRequest) {
        Rate saveRate = rateMapper.toRate(rateRequest);
        ifRateAlreadyExistsThrow(saveRate);
        try {
            Rate rate = rateRepository.save(saveRate);
            return rateMapper.toResponse(rate);
        } catch (Exception e) {
            throw new DbModificationAttemptException(getInvalidAttemptExceptionMessage("create", e.getMessage()));
        }
    }

    @Override
    @Transactional
    public RateResponse update(@Valid RateRequest rateRequest,
                               @Positive(message = "{validate.method.parameter.id.negative}") Long id) {
        Rate rate = findByIdOrThrow(id);
        try {
            rateMapper.updateRateFromDto(rateRequest, rate);
            RateResponse rateResponse = rateMapper.toResponse(rate);
            rateRepository.flush();
            return rateResponse;
        } catch (Exception e) {
            throw new DbModificationAttemptException(getInvalidAttemptExceptionMessage("update", e.getMessage()));
        }
    }

    @Override
    public void delete(@Positive(message = "{validate.method.parameter.id.negative}") Long id) {
        findByIdOrThrow(id);
        try {
            rateRepository.deleteById(id);
        } catch (Exception e) {
            throw new DbModificationAttemptException(getInvalidAttemptExceptionMessage("delete", e.getMessage()));
        }
    }

    private Rate findByIdOrThrow(Long id) {
        return rateRepository.findById(id)
                .orElseThrow(() -> new RateNotFoundException(getRateNotFoundExceptionMessage(id)));
    }

    private void ifRateAlreadyExistsThrow(Rate rate) {
        if (rateRepository.existsRateByPassengerIdAndDriverIdAndAuthor(
                rate.getPassengerId(), rate.getDriverId(), rate.getAuthor()
        )) {
            throw new RateAlreadyExistsException(getRateAlreadyExistsExceptionMessage());
        }
    }

    private String getRateNotFoundExceptionMessage(Long id) {
        return messageSource
                .getMessage(RATE_NOT_FOUND, new Object[] {id}, LocaleContextHolder.getLocale());
    }

    private String getRateListIsEmptyExceptionMessage(String constant, Long id) {
        return messageSource
                .getMessage(constant, new Object[] {id}, LocaleContextHolder.getLocale());
    }

    private String getInvalidAttemptExceptionMessage(String methodName, String exceptionMessage) {
        return messageSource
                .getMessage(INVALID_ATTEMPT_CHANGE_RATE, new Object[] {methodName, exceptionMessage},
                        LocaleContextHolder.getLocale());
    }

    private String getRateAlreadyExistsExceptionMessage() {
        return messageSource
                .getMessage(RATE_ALREADY_EXISTS, new Object[] {}, LocaleContextHolder.getLocale());
    }

}
