package com.example.driverservice.utility.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class KafkaListenerPropertyVariablesConstants {

    public static final String CONSUMER_TOPIC_PASSENGER_RATE = "${spring.kafka.consumer.topic-driver-rate}";
    public static final String CONSUMER_GROUP_ID = "${spring.kafka.consumer.group-id}";

}
