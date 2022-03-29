package com.news.scraper.config;

import com.github.instagram4j.instagram4j.IGClient;
import com.github.instagram4j.instagram4j.exceptions.IGLoginException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InstagramConfiguration {
    @Bean
    IGClient igclient() {
        try {
            return IGClient.builder()
                    .username("havades_ruz_iran")
                    .password("spmar1300")
                    .login();
        } catch (IGLoginException e) {
            e.printStackTrace();
        }
        return null;
    }
}
