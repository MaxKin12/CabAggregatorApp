package com.example.ratesservice.configuration;

import com.example.ratesservice.configuration.container.MySqlTestContainer;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.context.ImportTestcontainers;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.ActiveProfiles;

@CucumberContextConfiguration
@SpringBootTest(classes = TestConfig.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 9050)
@ImportTestcontainers(MySqlTestContainer.class)
@ActiveProfiles("test")
public class CucumberSpringConfiguration {
}
