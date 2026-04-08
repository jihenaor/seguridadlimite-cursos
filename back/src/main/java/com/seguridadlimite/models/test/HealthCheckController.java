package com.seguridadlimite.models.test;

import com.seguridadlimite.models.entity.Health;
import com.seguridadlimite.springboot.backend.apirest.services.HealthCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HealthCheckController {
    @Autowired
    HealthCheckService healthCheckService;

    @GetMapping("/health")
    public ResponseEntity<Health> healthCheck() {
        return ResponseEntity.ok(healthCheckService.consultarSaludSistema());
    }
}
