package com.example.passengerservice.configuration;

import static com.example.passengerservice.configuration.constants.GeneralUtilityConstants.DB_DOCKER_IMAGE;

import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@ActiveProfiles("test")
@Testcontainers
public class MysSqlContainer {

    @Container
    @ServiceConnection
    protected static final MySQLContainer<?> MYSQL_CONTAINER = new MySQLContainer<>(
            DockerImageName.parse(DB_DOCKER_IMAGE));

    static {
        MYSQL_CONTAINER.start();
    }

}
