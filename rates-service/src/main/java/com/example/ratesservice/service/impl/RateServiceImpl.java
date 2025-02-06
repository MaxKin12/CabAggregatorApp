package com.example.ratesservice.service.impl;

import static com.example.ratesservice.utility.constants.InternationalizationExceptionVariablesConstants.INVALID_ATTEMPT_CHANGE_RATE;
import static com.example.ratesservice.utility.constants.InternationalizationExceptionVariablesConstants.RATE_NOT_FOUND;

import com.example.ratesservice.dto.RatePageResponse;
import com.example.ratesservice.dto.RateRequest;
import com.example.ratesservice.dto.RateResponse;
import com.example.ratesservice.exception.custom.DbModificationAttemptException;
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
public class RateServiceImpl implements RateService {

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
    public RateResponse create(@Valid RateRequest rateRequest) {
        try {
            Rate saveRate = rateMapper.toRate(rateRequest);
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

    private String getRateNotFoundExceptionMessage(Long id) {
        return messageSource
                .getMessage(RATE_NOT_FOUND, new Object[] {id}, LocaleContextHolder.getLocale());
    }

    private String getInvalidAttemptExceptionMessage(String methodName, String exceptionMessage) {
        return messageSource
                .getMessage(INVALID_ATTEMPT_CHANGE_RATE, new Object[] {methodName, exceptionMessage},
                        LocaleContextHolder.getLocale());
    }

}
