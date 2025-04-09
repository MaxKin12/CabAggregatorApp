package com.example.ridesservice.configuration;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.instrumentation.jdbc.datasource.JdbcTelemetry;
import javax.sql.DataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class DbConfig {

//    @Bean
//    public DataSource dataSource(OpenTelemetry openTelemetry) {
//        DataSource originalDs = DataSourceBuilder.create()
//                .url("jdbc:mysql://rides-db:3306/ride_service")
//                .username("user")
//                .password("123456789abc")
//                .build();
//
//        return JdbcTelemetry.create(openTelemetry).wrap(originalDs);
//    }

}
