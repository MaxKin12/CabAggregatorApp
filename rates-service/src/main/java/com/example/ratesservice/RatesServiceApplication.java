package com.example.ratesservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class RatesServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RatesServiceApplication.class, args);
    }

}
