package com.github.uuidcode.springboot.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware", dateTimeProviderRef = "dateTimeProvider")
public class SpringbootSampleApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringbootSampleApplication.class, args);
    }
}
