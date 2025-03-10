package com.example.ridesservice.service.impl;

import static com.example.ridesservice.constants.RideTestData.RIDE;
import static com.example.ridesservice.constants.RideTestData.RIDE_ID;
import static com.example.ridesservice.utility.constants.InternationalizationExceptionVariablesConstants.QUEUE_INVALID_RIDE_SAVE;
import static com.example.ridesservice.utility.constants.InternationalizationExceptionVariablesConstants.QUEUE_IS_EMPTY;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.ridesservice.exception.custom.QueueIsEmptyException;
import com.example.ridesservice.exception.custom.QueueRideSaveException;
import com.example.ridesservice.model.QueueRide;
import com.example.ridesservice.model.Ride;
import com.example.ridesservice.repository.QueueRideRepository;
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

        assertNotNull(result);
        assertEquals(queueRide, result);
        verify(validation).findQueueOldestRecord();
        verify(queueRideRepository).delete(queueRide);
    }

    @Test
    void popRideTest_NoQueueRideFound_ThrowsException() {
        String[] args = new String[]{};

        when(validation.findQueueOldestRecord())
                .thenThrow(new QueueIsEmptyException(QUEUE_IS_EMPTY, args));

        QueueIsEmptyException exception = assertThrows(QueueIsEmptyException.class,
                () -> queueRideService.popRide());

        assertEquals(QUEUE_IS_EMPTY, exception.getMessageKey());
        assertArrayEquals(args, exception.getArgs());
        verify(validation).findQueueOldestRecord();
        verifyNoInteractions(queueRideRepository);
    }

}
