package com.example.passengerservice.configuration;

import static com.example.passengerservice.utility.constants.TestDbInitConstants.DB_DOCKER_IMAGE;
import static com.example.passengerservice.utility.constants.TestDbInitConstants.PROPERTY_DB_PASSWORD;
import static com.example.passengerservice.utility.constants.TestDbInitConstants.PROPERTY_DB_URL;
import static com.example.passengerservice.utility.constants.TestDbInitConstants.PROPERTY_DB_USERNAME;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
@ActiveProfiles("test")
public class DbContainer {

    @Container
    protected static final MySQLContainer<?> MYSQL_CONTAINER = new MySQLContainer<>(
            DockerImageName.parse(DB_DOCKER_IMAGE));

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add(PROPERTY_DB_URL, MYSQL_CONTAINER::getJdbcUrl);
        registry.add(PROPERTY_DB_USERNAME, MYSQL_CONTAINER::getUsername);
        registry.add(PROPERTY_DB_PASSWORD, MYSQL_CONTAINER::getPassword);
    }

    static  {
        MYSQL_CONTAINER.start();
    }

}
