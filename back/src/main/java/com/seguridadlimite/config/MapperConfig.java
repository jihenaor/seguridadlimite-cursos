package com.seguridadlimite.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {
    "com.seguridadlimite.models.evaluacion.dominio",
    "com.seguridadlimite.models.pregunta.domain",
    "com.seguridadlimite.models.grupopregunta.domain"
})
public class MapperConfig {
} 