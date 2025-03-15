package com.example.passengerservice.configuration;

import com.example.passengerservice.configuration.container.MySqlTestContainer;
import com.example.passengerservice.kafka.KafkaListenerService;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.context.ImportTestcontainers;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@CucumberContextConfiguration
@SpringBootTest(classes = TestConfig.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ImportTestcontainers(MySqlTestContainer.class)
public class CucumberSpringConfiguration {

    @MockitoBean
    protected KafkaConsumerConfig kafkaConsumerConfig;

    @MockitoBean
    protected KafkaListenerService kafkaConsumer;

}
