package com.seguridadlimite.util;

import org.hibernate.Interceptor;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HibernateConfig {

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer() {
        return hibernateProperties -> {
            hibernateProperties.put("hibernate.session_factory.interceptor", uppercaseInterceptor());
        };
    }

    @Bean
    public Interceptor uppercaseInterceptor() {
        return new UppercaseInterceptor();
    }
}
