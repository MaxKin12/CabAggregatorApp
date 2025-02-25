package com.example.driverservice.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.kafka.consumer")
public record KafkaProperties(

        String bootstrapServers,
        String topicDriverRate,
        String groupId

) {
}
