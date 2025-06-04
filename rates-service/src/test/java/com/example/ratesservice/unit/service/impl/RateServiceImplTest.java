package com.example.ratesservice.unit.service.impl;

import static com.example.ratesservice.configuration.constants.RateTestData.AVERAGE;
import static com.example.ratesservice.configuration.constants.RateTestData.AVERAGE_RATE;
import static com.example.ratesservice.configuration.constants.RateTestData.DRIVER_ID;
import static com.example.ratesservice.configuration.constants.RateTestData.INVALID_RATE_ID;
import static com.example.ratesservice.configuration.constants.RateTestData.LIMIT;
import static com.example.ratesservice.configuration.constants.RateTestData.OFFSET;
import static com.example.ratesservice.configuration.constants.RateTestData.RATE;
import static com.example.ratesservice.configuration.constants.RateTestData.RATE_AVERAGE_RESPONSE;
import static com.example.ratesservice.configuration.constants.RateTestData.RATE_CHANGE_EVENT;
import static com.example.ratesservice.configuration.constants.RateTestData.RATE_ID;
import static com.example.ratesservice.configuration.constants.RateTestData.RATE_PAGE;
import static com.example.ratesservice.configuration.constants.RateTestData.RATE_PAGE_RESPONSE;
import static com.example.ratesservice.configuration.constants.RateTestData.RATE_REQUEST;
import static com.example.ratesservice.configuration.constants.RateTestData.RATE_REQUEST_UPDATED;
import static com.example.ratesservice.configuration.constants.RateTestData.RATE_RESPONSE;
import static com.example.ratesservice.utility.constants.InternationalizationExceptionPropertyVariablesConstants.DRIVER_RATE_LIST_IS_EMPTY;
import static com.example.ratesservice.utility.constants.InternationalizationExceptionPropertyVariablesConstants.PASSENGER_RATE_LIST_IS_EMPTY;
import static com.example.ratesservice.utility.constants.InternationalizationExceptionPropertyVariablesConstants.RATE_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.example.ratesservice.dto.external.RidesResponse;
import com.example.ratesservice.dto.rate.response.RateAverageResponse;
import com.example.ratesservice.dto.rate.response.RatePageResponse;
import com.example.ratesservice.dto.rate.request.RateRequest;
import com.example.ratesservice.dto.rate.response.RateResponse;
import com.example.ratesservice.dto.rate.request.RateUpdateRequest;
import com.example.ratesservice.enums.RecipientType;
import com.example.ratesservice.exception.custom.RateListIsEmptyException;
import com.example.ratesservice.exception.custom.RateNotFoundException;
import com.example.ratesservice.mapper.rate.RateAverageMapper;
import com.example.ratesservice.mapper.rate.RateMapper;
import com.example.ratesservice.mapper.rate.RatePageMapper;
import com.example.ratesservice.model.Rate;
import com.example.ratesservice.model.RateChangeEvent;
import com.example.ratesservice.repository.RateEventsRepository;
import com.example.ratesservice.repository.RateRepository;
import com.example.ratesservice.service.impl.RateServiceImpl;
import com.example.ratesservice.utility.validation.RateServiceValidation;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
class RateServiceImplTest {

    @InjectMocks
    private RateServiceImpl rateService;

    @Mock
    private RateServiceValidation validation;

    @Mock
    private RateRepository rateRepository;

    @Mock
    private RateEventsRepository rateEventsRepository;

    @Mock
    private RateMapper rateMapper;

    @Mock
    private RatePageMapper ratePageMapper;

    @Mock
    private RateAverageMapper rateAverageMapper;

    @Test
    void findById_ValidId_ReturnsRateResponse() {
        UUID id = RATE_ID;
        Rate rate = RATE;
        RateResponse rateResponse = RATE_RESPONSE;

        when(validation.findByIdOrThrow(id)).thenReturn(rate);
        when(rateMapper.toResponse(rate)).thenReturn(rateResponse);

        RateResponse result = rateService.findById(id);

        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(rateResponse);
        verify(validation).findByIdOrThrow(id);
        verify(rateMapper).toResponse(rate);
    }

    @Test
    void findById_InvalidId_ThrowsException() {
        UUID id = INVALID_RATE_ID;
        String[] args = new String[]{id.toString()};

        when(validation.findByIdOrThrow(id)).thenThrow(new RateNotFoundException(RATE_NOT_FOUND, args));

        assertThatExceptionOfType(RateNotFoundException.class)
                .isThrownBy(() -> rateService.findById(id))
                .withMessage(RATE_NOT_FOUND)
                .satisfies(e -> assertThat(e.getArgs()).isEqualTo(args));

        verify(validation).findByIdOrThrow(id);
        verifyNoInteractions(rateMapper);
    }

    @Test
    void findAllByAuthor_ValidParameters_ReturnsRatePageResponse() {
        int offset = OFFSET;
        int limit = LIMIT;
        RecipientType recipientType = RecipientType.DRIVER;
        Page<Rate> ratePage = RATE_PAGE;
        RatePageResponse ratePageResponse = RATE_PAGE_RESPONSE;

        when(validation.cutDownLimit(limit)).thenReturn(limit);
        when(rateRepository.findAllByRecipient(PageRequest.of(offset, limit), recipientType)).thenReturn(ratePage);
        when(ratePageMapper.toResponsePage(ratePage, offset, limit)).thenReturn(ratePageResponse);

        RatePageResponse result = rateService.findAllByAuthor(offset, limit, recipientType);

        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(ratePageResponse);
        verify(validation).cutDownLimit(limit);
        verify(rateRepository).findAllByRecipient(PageRequest.of(offset, limit), recipientType);
        verify(ratePageMapper).toResponsePage(ratePage, offset, limit);
    }

    @Test
    void findAverageRate_ValidParameters_ReturnsRateAverageResponse() {
        UUID personId = DRIVER_ID;
        RecipientType recipientType = RecipientType.DRIVER;
        List<Rate> rateList = Collections.singletonList(new Rate());
        BigDecimal averageDecimal = AVERAGE_RATE;
        RateAverageResponse rateAverageResponse = RATE_AVERAGE_RESPONSE;

        when(validation.getLastRatesPage(personId, recipientType)).thenReturn(rateList);
        when(validation.countAverage(rateList, personId, recipientType)).thenReturn(AVERAGE);
        when(rateAverageMapper.toRateAverageResponse(personId, averageDecimal)).thenReturn(rateAverageResponse);

        RateAverageResponse result = rateService.findAverageRate(personId, recipientType);

        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(rateAverageResponse);
        verify(validation).getLastRatesPage(personId, recipientType);
        verify(validation).countAverage(rateList, personId, recipientType);
        verify(rateAverageMapper).toRateAverageResponse(personId, averageDecimal);
    }

    @Test
    void findAverageRate_EmptyRateList_ThrowsRateListIsEmptyException() {
        UUID personId = DRIVER_ID;
        RecipientType recipientType = RecipientType.DRIVER;
        String[] args = new String[]{personId.toString()};

        when(validation.getLastRatesPage(personId, recipientType))
                .thenThrow(new RateListIsEmptyException(PASSENGER_RATE_LIST_IS_EMPTY, args));

        assertThatExceptionOfType(RateListIsEmptyException.class)
                .isThrownBy(() -> rateService.findAverageRate(personId, recipientType))
                .withMessage(PASSENGER_RATE_LIST_IS_EMPTY)
                .satisfies(e -> assertThat(e.getArgs()).isEqualTo(args));

        verify(validation).getLastRatesPage(personId, recipientType);
        verifyNoInteractions(rateAverageMapper);
    }

    @Test
    void create_ValidRateRequest_ReturnsRateResponse() {
        RateRequest rateRequest = RATE_REQUEST;
        Rate rate = RATE;
        Rate savedRate = RATE;
        RateResponse rateResponse = RATE_RESPONSE;
        RidesResponse ridesResponse = RidesResponse.builder()
                .build();

        when(rateMapper.toRate(rateRequest)).thenReturn(rate);
        when(validation.getRideById(rate.getRideId())).thenReturn(ridesResponse);
        when(validation.saveOrThrow(rate)).thenReturn(savedRate);
        when(rateMapper.toResponse(savedRate)).thenReturn(rateResponse);

        RateResponse result = rateService.create(rateRequest);

        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(rateResponse);
        verify(rateMapper).toRate(rateRequest);
        verify(validation).getRideById(rate.getRideId());
        verify(validation).saveOrThrow(rate);
        verify(rateMapper).toResponse(savedRate);
    }

    @Test
    void update_ValidRateUpdateRequest_ReturnsRateResponse() {
        UUID id = RATE_ID;
        RateUpdateRequest rateUpdateRequest = RATE_REQUEST_UPDATED;
        Rate rate = RATE;
        RateResponse rateResponse = RATE_RESPONSE;

        when(validation.findByIdOrThrow(id)).thenReturn(rate);
        when(rateMapper.toResponse(rate)).thenReturn(rateResponse);

        RateResponse result = rateService.update(rateUpdateRequest, id);

        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(rateResponse);
        verify(validation).findByIdOrThrow(id);
        verify(validation).updateOrThrow(rate, rateUpdateRequest);
        verify(rateMapper).toResponse(rate);
    }

    @Test
    void delete_ValidId_ReturnsRateResponse() {
        UUID id = RATE_ID;
        Rate rate = RATE;
        RateResponse rateResponse = RATE_RESPONSE;

        when(validation.findByIdOrThrow(id)).thenReturn(rate);
        when(rateMapper.toResponse(rate)).thenReturn(rateResponse);

        RateResponse result = rateService.delete(id);

        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(rateResponse);
        verify(validation).findByIdOrThrow(id);
        verify(rateRepository).deleteById(id);
        verify(rateMapper).toResponse(rate);
    }

    @Test
    void updateAverageRate_ValidRateResponse_UpdatesAverageRate() {
        RateResponse rateResponse = RATE_RESPONSE;
        RateAverageResponse rateAverageResponse = RATE_AVERAGE_RESPONSE;
        RateChangeEvent event = RATE_CHANGE_EVENT;

        when(rateService.findAverageRate(rateResponse.driverId(), RecipientType.DRIVER))
                .thenReturn(rateAverageResponse);
        when(rateEventsRepository.save(any(RateChangeEvent.class))).thenReturn(event);

        rateService.updateAverageRate(rateResponse);

        verify(rateEventsRepository).save(any(RateChangeEvent.class));
    }

    @Test
    void updateAverageRate_RateListIsEmpty_DoesNothing() {
        RateResponse rateResponse = RATE_RESPONSE;
        String[] args = new String[]{rateResponse.driverId().toString()};

        when(rateService.findAverageRate(rateResponse.driverId(), RecipientType.DRIVER))
                .thenThrow(new RateListIsEmptyException(DRIVER_RATE_LIST_IS_EMPTY, args));

        rateService.updateAverageRate(rateResponse);

        verifyNoInteractions(rateEventsRepository);
    }

    @Test
    void deleteTest_ValidId_DeletesRide() {
        UUID id = RATE_ID;

        when(validation.findByIdOrThrow(id)).thenReturn(RATE);
        doNothing().when(rateRepository).deleteById(id);

        assertThatNoException().isThrownBy(() -> rateService.delete(id));

        verify(validation).findByIdOrThrow(id);
        verify(rateRepository).deleteById(id);
    }

    @Test
    void deleteTest_InvalidId_ThrowsException() {
        UUID id = INVALID_RATE_ID;
        String[] args = new String[]{id.toString()};

        when(validation.findByIdOrThrow(id))
                .thenThrow(new RateNotFoundException(RATE_NOT_FOUND, args));

        assertThatExceptionOfType(RateNotFoundException.class)
                .isThrownBy(() -> rateService.delete(id))
                .withMessage(RATE_NOT_FOUND)
                .satisfies(e -> assertThat(e.getArgs()).isEqualTo(args));

        verify(validation).findByIdOrThrow(id);
        verifyNoInteractions(rateRepository);
    }

}
