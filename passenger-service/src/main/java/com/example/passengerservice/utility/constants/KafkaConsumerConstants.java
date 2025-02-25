package com.example.passengerservice.utility.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class KafkaConsumerConstants {

    public static final String CONSUMER_TOPIC_PASSENGER_RATE = "${spring.kafka.consumer.topic-passenger-rate}";
    public static final String CONSUMER_GROUP_ID = "${spring.kafka.consumer.group-id}";

}
