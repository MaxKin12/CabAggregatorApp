package com.example.ratesservice.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.kafka.producer")
public record KafkaProperties(

        String bootstrapServers,
        String topicPassenger,
        Integer topicPassengerPartitionsCount,
        Integer topicPassengerReplicasCount,
        String topicDriver,
        Integer topicDriverPartitionsCount,
        Integer topicDriverReplicasCount,
        Integer schedulerProcessingBatchSize

) {
}
