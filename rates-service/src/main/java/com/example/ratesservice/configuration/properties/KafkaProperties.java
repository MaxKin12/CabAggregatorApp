package com.example.ratesservice.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.kafka.producer")
public record KafkaProperties(

        String topicPassengerRate,

        String topicDriverRate,

        String bootstrapServers,

        Integer schedulerProcessingBatchSize

) {
}
