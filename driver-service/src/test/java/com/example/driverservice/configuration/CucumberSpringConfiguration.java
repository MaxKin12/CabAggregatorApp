package com.example.driverservice.configuration;

import com.example.driverservice.configuration.container.MySqlTestContainer;
import com.example.driverservice.kafka.KafkaListenerService;
import com.example.driverservice.utility.validation.KafkaConsumerServiceValidation;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.context.ImportTestcontainers;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@CucumberContextConfiguration
@SpringBootTest(classes = TestConfig.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ImportTestcontainers(MySqlTestContainer.class)
public class CucumberSpringConfiguration {

    @MockitoBean
    protected KafkaConsumerConfig kafkaProducerConfig;

    @MockitoBean
    protected KafkaListenerService kafkaListener;

}
