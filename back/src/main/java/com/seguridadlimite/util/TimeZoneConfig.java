package com.seguridadlimite.util;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

import java.util.TimeZone;

@Configuration
public class TimeZoneConfig {

    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("America/Bogota"));
        System.out.println("Zona horaria configurada a: " + TimeZone.getDefault().getID());
    }
}
