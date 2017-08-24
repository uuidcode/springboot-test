package com.github.uuidcode.springboot.test.configuration;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.GregorianCalendar;
import java.util.Random;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;

import com.github.uuidcode.springboot.test.service.CoreService;

@Configuration
public class JpaAuditingConfiguration {
    @Resource
    private CoreService coreService;

    @Bean
    public AuditorAware<Long> auditorAware() {
        return () -> Math.abs(new Random().nextLong());
    }

    @Bean
    public DateTimeProvider dateTimeProvider() {
        LocalDateTime localDateTime = this.coreService.now();
        return () -> GregorianCalendar.from(ZonedDateTime.of(localDateTime, ZoneId.systemDefault()));
    }
}
