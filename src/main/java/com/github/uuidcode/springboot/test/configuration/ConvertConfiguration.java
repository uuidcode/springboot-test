package com.github.uuidcode.springboot.test.configuration;

import com.github.uuidcode.springboot.test.utils.CoreUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

@Configuration
public class ConvertConfiguration extends WebMvcConfigurerAdapter {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converterList) {
        converterList.add(new MappingJackson2HttpMessageConverter(CoreUtil.objectMapper));
    }
}