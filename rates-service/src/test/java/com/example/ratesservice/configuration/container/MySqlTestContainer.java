package com.example.ratesservice.configuration.container;

import static com.example.ratesservice.configuration.constants.GeneralUtilityConstants.DB_DOCKER_IMAGE;

import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

@ActiveProfiles("test")
public abstract class MySqlTestContainer {

    @ServiceConnection
    protected static final MySQLContainer<?> MYSQL_CONTAINER = new MySQLContainer<>(
            DockerImageName.parse(DB_DOCKER_IMAGE));

    static {
        MYSQL_CONTAINER.start();
    }

}
