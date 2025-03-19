package com.example.ridesservice.unit.utility.validation.impl;

import static com.example.ridesservice.configuration.constants.RideTestData.RIDE_ID;
import static com.example.ridesservice.utility.constants.InternationalizationExceptionVariablesConstants.QUEUE_INVALID_RIDE_SAVE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.ridesservice.exception.custom.QueueIsEmptyException;
import com.example.ridesservice.exception.custom.QueueRideSaveException;
import com.example.ridesservice.model.QueueRide;
import com.example.ridesservice.repository.QueueRideRepository;
import com.example.ridesservice.utility.validation.impl.QueueRideServiceValidationImpl;
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
        QueueRide expectedQueueRide = QueueRide.builder()
                .rideId(RIDE_ID)
                .build();

        when(queueRideRepository.findTop1ByOrderByChangedAt()).thenReturn(Optional.of(expectedQueueRide));

        QueueRide actualQueueRide = queueRideServiceValidation.findQueueOldestRecord();

        assertThat(actualQueueRide).isNotNull();
        assertThat(actualQueueRide.getRideId()).isEqualTo(expectedQueueRide.getRideId());
        verify(queueRideRepository).findTop1ByOrderByChangedAt();
    }

    @Test
    void findQueueOldestRecord_ShouldThrowQueueIsEmptyException_WhenNoRecordExists() {
        when(queueRideRepository.findTop1ByOrderByChangedAt()).thenReturn(Optional.empty());

        assertThatExceptionOfType(QueueIsEmptyException.class)
                .isThrownBy(() -> queueRideServiceValidation.findQueueOldestRecord());

        verify(queueRideRepository).findTop1ByOrderByChangedAt();
    }

    @Test
    void saveOrThrow_ShouldSaveQueueRide_WhenSaveIsSuccessful() {
        QueueRide queueRide = new QueueRide();

        assertThatNoException().isThrownBy(() -> queueRideServiceValidation.saveOrThrow(queueRide));

        verify(queueRideRepository).save(queueRide);
    }

    @Test
    void saveOrThrow_ShouldThrowQueueRideSaveException_WhenSaveFails() {
        QueueRide queueRide = new QueueRide();
        String[] args = new String[] {QUEUE_INVALID_RIDE_SAVE};

        doThrow(new QueueRideSaveException(QUEUE_INVALID_RIDE_SAVE, args))
                .when(queueRideRepository).save(queueRide);

        assertThatExceptionOfType(QueueRideSaveException.class)
                .isThrownBy(() -> queueRideServiceValidation.saveOrThrow(queueRide))
                .withMessage(QUEUE_INVALID_RIDE_SAVE)
                .satisfies(e -> assertThat(e.getArgs()).isEqualTo(args));

        verify(queueRideRepository).save(queueRide);
    }

}
