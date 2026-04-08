package com.seguridadlimite.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;

/**
 * Registro de filtros HTTP.
 * El RateLimitingFilter se aplica a toda la API antes que cualquier otro filtro.
 */
@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<RateLimitingFilter> rateLimitingFilter(
            RateLimitingFilter rateLimitingFilter) {
        FilterRegistrationBean<RateLimitingFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(rateLimitingFilter);
        registrationBean.addUrlPatterns("/*");   // Aplica a toda la app (incluyendo /auth)
        registrationBean.setOrder(1);            // Primer filtro en ejecutarse
        return registrationBean;
    }
}
