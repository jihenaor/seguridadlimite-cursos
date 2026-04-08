package com.seguridadlimite.springboot.backend.apirest.services;

import com.seguridadlimite.models.entity.Health;
import org.springframework.stereotype.Service;

@Service
public class HealthCheckService {
    public Health consultarSaludSistema() {
        return new Health("OK", "OK", "OK");
    }
}
