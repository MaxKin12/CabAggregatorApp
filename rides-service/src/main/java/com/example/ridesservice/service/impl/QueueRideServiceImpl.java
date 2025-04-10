package com.example.ridesservice.service.impl;

import static com.example.ridesservice.utility.constants.LogMessagesTemplate.RIDE_EXTRACTED_LOG_TEMPLATE;
import static com.example.ridesservice.utility.constants.LogMessagesTemplate.RIDE_PLACED_LOG_TEMPLATE;

import com.example.ridesservice.model.QueueRide;
import com.example.ridesservice.model.Ride;
import com.example.ridesservice.repository.QueueRideRepository;
import com.example.ridesservice.service.QueueRideService;
import com.example.ridesservice.utility.validation.QueueRideServiceValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class QueueRideServiceImpl implements QueueRideService {

    private final QueueRideServiceValidation validation;
    private final QueueRideRepository queueRideRepository;

    @Override
    @Transactional
    public QueueRide popRide() {
        QueueRide queueRide = validation.findQueueOldestRecord();
        queueRideRepository.delete(queueRide);
        log.info(RIDE_EXTRACTED_LOG_TEMPLATE, queueRide);
        return queueRide;
    }

    @Override
    @Transactional
    public void append(Ride ride) {
        QueueRide queueRide = QueueRide
                .builder()
                .rideId(ride.getId())
                .build();
        validation.saveOrThrow(queueRide);
        log.info(RIDE_PLACED_LOG_TEMPLATE, queueRide);
    }

}
