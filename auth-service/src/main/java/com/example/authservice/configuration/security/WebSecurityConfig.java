package com.example.authservice.configuration.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtAuthConverter jwtAuthConverter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthConverter))
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers(
                        HttpMethod.POST,
                        "/api/v1/users/login",
                        "/api/v1/users/refresh",
                        "/api/v1/users/passenger",
                        "/api/v1/users/driver"
                )
                .requestMatchers(
                        HttpMethod.OPTIONS,
                        "/**"
                )
                .requestMatchers(
                        "/api/v1/users/swagger-ui.html",
                        "/api/v1/users/swagger-ui/**",
                        "/api/v1/users/swagger-resources/**",
                        "/api/v1/users/api-docs",
                        "/api/v1/users/api-docs/**",
                        "/api/v1/users/webjars/**"
                );
    }

}
