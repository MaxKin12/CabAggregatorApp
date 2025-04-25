package com.example.gatewayservice.configuration;

import com.example.gatewayservice.configuration.jwt.JwtAuthConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers(
                                "/api/v1/users/login",
                                "/api/v1/users/refresh",
                                "/api/v1/users/passenger",
                                "/api/v1/users/driver",
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/swagger-resources/**",
                                "/api-docs/**",
                                "/webjars/**",
                                "/actuator/**"
                        ).permitAll()
                        .pathMatchers(
                                HttpMethod.GET,
                                "/api/v1/passengers"
                        ).hasAnyRole("passenger", "admin")
                        .pathMatchers(
                                "/api/v1/passengers/**"
                        ).hasAnyRole("passenger")
                        .pathMatchers(
                                HttpMethod.GET,
                                "/api/v1/drivers",
                                "/api/v1/cars"
                        ).hasAnyRole("driver", "admin")
                        .pathMatchers(
                                "/api/v1/drivers/**",
                                "/api/v1/cars/**"
                        ).hasAnyRole("driver")
                        .pathMatchers(
                                "/api/v1/users/logout",
                                "/api/v1/rates/**",
                                "/api/v1/rides/**"
                        ).hasAnyRole("passenger", "driver", "admin")
                        .anyExchange().hasRole("admin"))
                .oauth2ResourceServer(oAuth2ResourceServerSpec -> oAuth2ResourceServerSpec
                        .jwt(jwt -> jwt
                                .jwtAuthenticationConverter(
                                        new ReactiveJwtAuthenticationConverterAdapter(
                                                new JwtAuthConverter()))))
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .build();
    }

}
