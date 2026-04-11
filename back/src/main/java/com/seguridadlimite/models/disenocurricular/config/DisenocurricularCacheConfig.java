package com.seguridadlimite.models.disenocurricular.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.time.Duration;

@Configuration
@EnableCaching
public class DisenocurricularCacheConfig {

    public static final String CACHE_DISENO_POR_NIVEL = "disenocurricularPorNivel";

    @Bean
    @Primary
    public CacheManager disenocurricularCacheManager() {
        CaffeineCacheManager manager = new CaffeineCacheManager(CACHE_DISENO_POR_NIVEL);
        manager.setCaffeine(Caffeine.newBuilder()
                .maximumSize(5_000)
                .expireAfterWrite(Duration.ofDays(30))
                .recordStats());
        return manager;
    }
}
