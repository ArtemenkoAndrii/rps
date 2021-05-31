package com.example.rps;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public Random resolveRandomizer() {
        return new Random();
    }
}
