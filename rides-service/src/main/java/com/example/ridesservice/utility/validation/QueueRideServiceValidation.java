package com.example.ridesservice.utility.validation;

import com.example.ridesservice.model.QueueRide;

public interface QueueRideServiceValidation {

    QueueRide findQueueOldestRecord();

    void saveOrThrow(QueueRide queueRide);

}
