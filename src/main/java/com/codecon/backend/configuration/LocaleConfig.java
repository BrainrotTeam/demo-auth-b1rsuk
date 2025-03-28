package com.codecon.backend.configuration;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;

@Configuration
public class LocaleConfig {

    @Bean
    public Locale defaultLocale() {
        Locale.setDefault(Locale.ENGLISH);
        return Locale.ENGLISH;
    }
}