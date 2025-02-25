package com.example.passengerservice.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.kafka.consumer")
public record KafkaProperties(

        String bootstrapServers,
        String topicPassengerRate,
        String groupId

) {
}
