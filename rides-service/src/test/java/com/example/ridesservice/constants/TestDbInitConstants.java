package com.example.ridesservice.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TestDbInitConstants {

    public final static String DB_DOCKER_IMAGE = "mysql:latest";
    public final static String PROPERTY_DB_URL = "spring.datasource.url";
    public final static String PROPERTY_DB_USERNAME = "spring.datasource.username";
    public final static String PROPERTY_DB_PASSWORD = "spring.datasource.password";

}
