package com.example.ratesservice.unit.utility.validation.impl;

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

import com.example.ratesservice.client.dto.RidesResponse;
import com.example.ratesservice.dto.rate.*;
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
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
class RateServiceValidationImplTest {

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
        when(validation.findByIdOrThrow(RATE_ID)).thenReturn(RATE);
        when(rateMapper.toResponse(RATE)).thenReturn(RATE_RESPONSE);

        RateResponse result = rateService.findById(RATE_ID);

        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(RATE_RESPONSE);
        verify(validation).findByIdOrThrow(RATE_ID);
        verify(rateMapper).toResponse(RATE);
    }

    @Test
    void findById_InvalidId_ThrowsException() {
        String[] args = new String[]{INVALID_RATE_ID.toString()};

        when(validation.findByIdOrThrow(INVALID_RATE_ID)).thenThrow(new RateNotFoundException(RATE_NOT_FOUND, args));

        assertThatExceptionOfType(RateNotFoundException.class)
                .isThrownBy(() -> rateService.findById(INVALID_RATE_ID))
                .withMessage(RATE_NOT_FOUND)
                .satisfies(e -> assertThat(e.getArgs()).isEqualTo(args));

        verify(validation).findByIdOrThrow(INVALID_RATE_ID);
        verifyNoInteractions(rateMapper);
    }

    @Test
    void findAllByAuthor_ValidParameters_ReturnsRatePageResponse() {
        when(validation.cutDownLimit(LIMIT)).thenReturn(LIMIT);
        when(rateRepository.findAllByRecipient(PageRequest.of(OFFSET, LIMIT), RecipientType.DRIVER)).thenReturn(RATE_PAGE);
        when(ratePageMapper.toResponsePage(RATE_PAGE, OFFSET, LIMIT)).thenReturn(RATE_PAGE_RESPONSE);

        RatePageResponse result = rateService.findAllByAuthor(OFFSET, LIMIT, RecipientType.DRIVER);

        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(RATE_PAGE_RESPONSE);
        verify(validation).cutDownLimit(LIMIT);
        verify(rateRepository).findAllByRecipient(PageRequest.of(OFFSET, LIMIT), RecipientType.DRIVER);
        verify(ratePageMapper).toResponsePage(RATE_PAGE, OFFSET, LIMIT);
    }

    @Test
    void findAverageRate_ValidParameters_ReturnsRateAverageResponse() {
        List<Rate> rateList = Collections.singletonList(new Rate());

        when(validation.getLastRatesPage(DRIVER_ID, RecipientType.DRIVER)).thenReturn(rateList);
        when(validation.countAverage(rateList, DRIVER_ID, RecipientType.DRIVER)).thenReturn(AVERAGE);
        when(rateAverageMapper.toRateAverageResponse(DRIVER_ID, AVERAGE_RATE)).thenReturn(RATE_AVERAGE_RESPONSE);

        RateAverageResponse result = rateService.findAverageRate(DRIVER_ID, RecipientType.DRIVER);

        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(RATE_AVERAGE_RESPONSE);
        verify(validation).getLastRatesPage(DRIVER_ID, RecipientType.DRIVER);
        verify(validation).countAverage(rateList, DRIVER_ID, RecipientType.DRIVER);
        verify(rateAverageMapper).toRateAverageResponse(DRIVER_ID, AVERAGE_RATE);
    }

    @Test
    void findAverageRate_EmptyRateList_ThrowsRateListIsEmptyException() {
        String[] args = new String[]{DRIVER_ID.toString()};

        when(validation.getLastRatesPage(DRIVER_ID, RecipientType.DRIVER))
                .thenThrow(new RateListIsEmptyException(PASSENGER_RATE_LIST_IS_EMPTY, args));

        assertThatExceptionOfType(RateListIsEmptyException.class)
                .isThrownBy(() -> rateService.findAverageRate(DRIVER_ID, RecipientType.DRIVER))
                .withMessage(PASSENGER_RATE_LIST_IS_EMPTY)
                .satisfies(e -> assertThat(e.getArgs()).isEqualTo(args));

        verify(validation).getLastRatesPage(DRIVER_ID, RecipientType.DRIVER);
        verifyNoInteractions(rateAverageMapper);
    }

    @Test
    void create_ValidRateRequest_ReturnsRateResponse() {
        RidesResponse ridesResponse = RidesResponse.builder().build();

        when(rateMapper.toRate(RATE_REQUEST)).thenReturn(RATE);
        when(validation.getRideById(RATE.getRideId())).thenReturn(ridesResponse);
        when(validation.saveOrThrow(RATE)).thenReturn(RATE);
        when(rateMapper.toResponse(RATE)).thenReturn(RATE_RESPONSE);

        RateResponse result = rateService.create(RATE_REQUEST);

        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(RATE_RESPONSE);
        verify(rateMapper).toRate(RATE_REQUEST);
        verify(validation).getRideById(RATE.getRideId());
        verify(validation).saveOrThrow(RATE);
        verify(rateMapper).toResponse(RATE);
    }

    @Test
    void update_ValidRateUpdateRequest_ReturnsRateResponse() {
        when(validation.findByIdOrThrow(RATE_ID)).thenReturn(RATE);
        when(rateMapper.toResponse(RATE)).thenReturn(RATE_RESPONSE);

        RateResponse result = rateService.update(RATE_REQUEST_UPDATED, RATE_ID);

        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(RATE_RESPONSE);
        verify(validation).findByIdOrThrow(RATE_ID);
        verify(validation).updateOrThrow(RATE, RATE_REQUEST_UPDATED);
        verify(rateMapper).toResponse(RATE);
    }

    @Test
    void delete_ValidId_ReturnsRateResponse() {
        when(validation.findByIdOrThrow(RATE_ID)).thenReturn(RATE);
        when(rateMapper.toResponse(RATE)).thenReturn(RATE_RESPONSE);

        RateResponse result = rateService.delete(RATE_ID);

        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(RATE_RESPONSE);
        verify(validation).findByIdOrThrow(RATE_ID);
        verify(rateRepository).deleteById(RATE_ID);
        verify(rateMapper).toResponse(RATE);
    }

    @Test
    void updateAverageRate_ValidRateResponse_UpdatesAverageRate() {
        when(rateService.findAverageRate(RATE_RESPONSE.driverId(), RecipientType.DRIVER))
                .thenReturn(RATE_AVERAGE_RESPONSE);
        when(rateEventsRepository.save(any(RateChangeEvent.class))).thenReturn(RATE_CHANGE_EVENT);

        rateService.updateAverageRate(RATE_RESPONSE);

        verify(rateEventsRepository).save(any(RateChangeEvent.class));
    }

    @Test
    void updateAverageRate_RateListIsEmpty_DoesNothing() {
        String[] args = new String[]{RATE_RESPONSE.driverId().toString()};

        when(rateService.findAverageRate(RATE_RESPONSE.driverId(), RecipientType.DRIVER))
                .thenThrow(new RateListIsEmptyException(DRIVER_RATE_LIST_IS_EMPTY, args));

        rateService.updateAverageRate(RATE_RESPONSE);

        verifyNoInteractions(rateEventsRepository);
    }

    @Test
    void deleteTest_ValidId_DeletesRide() {
        when(validation.findByIdOrThrow(RATE_ID)).thenReturn(RATE);
        doNothing().when(rateRepository).deleteById(RATE_ID);

        assertThatNoException().isThrownBy(() -> rateService.delete(RATE_ID));

        verify(validation).findByIdOrThrow(RATE_ID);
        verify(rateRepository).deleteById(RATE_ID);
    }

    @Test
    void deleteTest_InvalidId_ThrowsException() {
        String[] args = new String[]{INVALID_RATE_ID.toString()};

        when(validation.findByIdOrThrow(INVALID_RATE_ID))
                .thenThrow(new RateNotFoundException(RATE_NOT_FOUND, args));

        assertThatExceptionOfType(RateNotFoundException.class)
                .isThrownBy(() -> rateService.delete(INVALID_RATE_ID))
                .withMessage(RATE_NOT_FOUND)
                .satisfies(e -> assertThat(e.getArgs()).isEqualTo(args));

        verify(validation).findByIdOrThrow(INVALID_RATE_ID);
        verifyNoInteractions(rateRepository);
    }

}
