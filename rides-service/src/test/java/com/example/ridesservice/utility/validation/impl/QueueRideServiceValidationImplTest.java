package com.example.ridesservice.utility.validation.impl;

import static com.example.ridesservice.constants.GeneralUtilityConstants.EXCEPTION_MESSAGE;
import static com.example.ridesservice.constants.RideTestData.RIDE_ID;
import static com.example.ridesservice.utility.constants.InternationalizationExceptionVariablesConstants.QUEUE_INVALID_RIDE_SAVE;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.ridesservice.exception.custom.QueueIsEmptyException;
import com.example.ridesservice.exception.custom.QueueRideSaveException;
import com.example.ridesservice.model.QueueRide;
import com.example.ridesservice.repository.QueueRideRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class QueueRideServiceValidationImplTest {

    @InjectMocks
    private QueueRideServiceValidationImpl queueRideServiceValidation;

    @Mock
    private QueueRideRepository queueRideRepository;

    @Test
    void findQueueOldestRecord_ShouldReturnOldestRecord_WhenRecordExists() {
        QueueRide expectedQueueRide = QueueRide
                .builder()
                .id(RIDE_ID)
                .build();

        when(queueRideRepository.findTop1ByOrderByChangedAt()).thenReturn(Optional.of(expectedQueueRide));

        QueueRide actualQueueRide = queueRideServiceValidation.findQueueOldestRecord();

        assertNotNull(actualQueueRide);
        assertEquals(expectedQueueRide.getId(), actualQueueRide.getId());
        verify(queueRideRepository).findTop1ByOrderByChangedAt();
    }

    @Test
    void findQueueOldestRecord_ShouldThrowQueueIsEmptyException_WhenNoRecordExists() {
        when(queueRideRepository.findTop1ByOrderByChangedAt()).thenReturn(Optional.empty());

        assertThrows(QueueIsEmptyException.class, () -> queueRideServiceValidation.findQueueOldestRecord());

        verify(queueRideRepository).findTop1ByOrderByChangedAt();
    }

    @Test
    void saveOrThrow_ShouldSaveQueueRide_WhenSaveIsSuccessful() {
        QueueRide queueRide = new QueueRide();

        assertDoesNotThrow(() -> queueRideServiceValidation.saveOrThrow(queueRide));

        verify(queueRideRepository).save(queueRide);
    }

    @Test
    void saveOrThrow_ShouldThrowQueueRideSaveException_WhenSaveFails() {
        QueueRide queueRide = new QueueRide();
        String[] args = new String[] {EXCEPTION_MESSAGE};

        doThrow(new QueueRideSaveException(QUEUE_INVALID_RIDE_SAVE, args))
                .when(queueRideRepository).save(queueRide);

        QueueRideSaveException exception = assertThrows(QueueRideSaveException.class,
                () -> queueRideServiceValidation.saveOrThrow(queueRide));

        assertEquals(QUEUE_INVALID_RIDE_SAVE, exception.getMessageKey());
        verify(queueRideRepository).save(queueRide);
    }

}
