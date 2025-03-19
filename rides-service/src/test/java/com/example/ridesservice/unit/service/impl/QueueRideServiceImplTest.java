package com.example.ridesservice.unit.service.impl;

import static com.example.ridesservice.configuration.constants.GeneralUtilityConstants.EXCEPTION_ARGS_FIELD;
import static com.example.ridesservice.configuration.constants.RideTestData.RIDE_ID;
import static com.example.ridesservice.utility.constants.InternationalizationExceptionVariablesConstants.QUEUE_IS_EMPTY;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.example.ridesservice.exception.custom.QueueIsEmptyException;
import com.example.ridesservice.model.QueueRide;
import com.example.ridesservice.repository.QueueRideRepository;
import com.example.ridesservice.service.impl.QueueRideServiceImpl;
import com.example.ridesservice.utility.validation.QueueRideServiceValidation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class QueueRideServiceImplTest {

    @InjectMocks
    private QueueRideServiceImpl queueRideService;

    @Mock
    private QueueRideServiceValidation validation;

    @Mock
    private QueueRideRepository queueRideRepository;

    @Test
    void popRideTest_ValidQueueRide_ReturnsQueueRide() {
        QueueRide queueRide = QueueRide
                .builder()
                .rideId(RIDE_ID)
                .build();

        when(validation.findQueueOldestRecord()).thenReturn(queueRide);
        doNothing().when(queueRideRepository).delete(queueRide);

        QueueRide result = queueRideService.popRide();

        assertThat(result)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(queueRide);
        verify(validation).findQueueOldestRecord();
        verify(queueRideRepository).delete(queueRide);
    }

    @Test
    void popRideTest_NoQueueRideFound_ThrowsException() {
        String[] args = new String[]{};

        when(validation.findQueueOldestRecord())
                .thenThrow(new QueueIsEmptyException(QUEUE_IS_EMPTY, args));

        assertThatThrownBy(() -> queueRideService.popRide())
                .isInstanceOf(QueueIsEmptyException.class)
                .hasMessage(QUEUE_IS_EMPTY)
                .hasFieldOrPropertyWithValue(EXCEPTION_ARGS_FIELD, args);
        verify(validation).findQueueOldestRecord();
        verifyNoInteractions(queueRideRepository);
    }

}
