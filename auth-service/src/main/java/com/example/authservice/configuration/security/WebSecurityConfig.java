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
//                        .requestMatchers(HttpMethod.POST, "/api/v1/users/**").permitAll()
//                        .requestMatchers(HttpMethod.POST, "/api/v1/users/driver").permitAll()
//                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
//                        .requestMatchers(
//                                "/swagger-ui/**",
//                                "/swagger-resources/**",
//                                "/swagger-ui.html",
//                                "/api-docs/**"
//                        ).permitAll()
                        .anyRequest().authenticated()

//                        .requestMatchers(HttpMethod.GET, "/test/admin", "/test/admin/**").hasRole(ADMIN)
//                        .requestMatchers(HttpMethod.GET, "/test/user").hasRole(USER)
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthConverter))
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {

        return (web) -> {
            web.ignoring().requestMatchers(
                    HttpMethod.POST,
                    "/api/v1/users/**"
            );
            web.ignoring().requestMatchers(
                    HttpMethod.POST,
                    "/api/v1/users/login"
            );
            web.ignoring().requestMatchers(
                    HttpMethod.GET,
                    "/actuator/**"
            );
            web.ignoring().requestMatchers(
                            HttpMethod.OPTIONS,
                            "/**"
                    )
                    .requestMatchers("**/swagger-ui/**", "**/swagger-resources/**", "**/swagger-ui.html",
                            "**/api-docs/**");

        };
    }

}
