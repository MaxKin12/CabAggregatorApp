package com.example.ridesservice.configuration;

import io.micrometer.core.instrument.binder.okhttp3.OkHttpObservationInterceptor;
import io.micrometer.observation.ObservationRegistry;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OkHttpClientConfig {

    @Bean
    public OkHttpClient okHttpClient(ObservationRegistry observationRegistry) {
        return new OkHttpClient.Builder()
                .addInterceptor(
                        OkHttpObservationInterceptor.builder(observationRegistry, "okhttp.requests")
                                .uriMapper(request -> request.url().encodedPath())
                                .build()
                )
                .build();
    }

}
