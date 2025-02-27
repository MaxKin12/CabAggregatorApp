package com.example.ridesservice.utility.validation.impl;

import static com.example.ridesservice.utility.constants.InternationalizationExceptionVariablesConstants.QUEUE_INVALID_RIDE_SAVE;
import static com.example.ridesservice.utility.constants.InternationalizationExceptionVariablesConstants.QUEUE_IS_EMPTY;

import com.example.ridesservice.exception.custom.QueueIsEmptyException;
import com.example.ridesservice.exception.custom.QueueRideSaveException;
import com.example.ridesservice.model.QueueRide;
import com.example.ridesservice.repository.QueueRideRepository;
import com.example.ridesservice.utility.validation.QueueRideServiceValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueueRideServiceValidationImpl implements QueueRideServiceValidation {

    private final QueueRideRepository queueRideRepository;

    @Override
    public QueueRide findQueueOldestRecord() {
        return queueRideRepository.findTop1ByOrderByChangedAt()
                .orElseThrow(() -> new QueueIsEmptyException(QUEUE_IS_EMPTY));
    }

    @Override
    public void saveOrThrow(QueueRide queueRide) {
        try {
            queueRideRepository.save(queueRide);
        } catch (Exception e) {
            throw new QueueRideSaveException(QUEUE_INVALID_RIDE_SAVE, e.getMessage());
        }
    }

}
