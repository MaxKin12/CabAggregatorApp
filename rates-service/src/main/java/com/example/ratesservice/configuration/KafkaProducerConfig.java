package com.example.ratesservice.configuration;

import com.example.ratesservice.configuration.properties.KafkaProperties;
import com.example.ratesservice.dto.kafkaevent.RateChangeEventResponse;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin.NewTopics;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Configuration
@RequiredArgsConstructor
public class KafkaProducerConfig {

    private final KafkaProperties kafkaProperties;

    @Bean
    public ProducerFactory<String, RateChangeEventResponse> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.bootstrapServers());
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, RateChangeEventResponse> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public NewTopics topics() {
        return new NewTopics(
                TopicBuilder.name(kafkaProperties.topicPassenger())
                        .partitions(kafkaProperties.topicPassengerPartitionsCount())
                        .replicas(kafkaProperties.topicPassengerReplicasCount())
                        .build(),
                TopicBuilder.name(kafkaProperties.topicDriver())
                        .partitions(kafkaProperties.topicDriverPartitionsCount())
                        .replicas(kafkaProperties.topicDriverReplicasCount())
                        .build());
    }

}
