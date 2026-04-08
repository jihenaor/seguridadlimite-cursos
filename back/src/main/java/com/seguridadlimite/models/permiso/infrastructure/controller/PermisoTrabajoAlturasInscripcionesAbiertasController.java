package com.seguridadlimite.models.permiso.infrastructure.controller;

import com.seguridadlimite.models.permiso.application.service.VerificarInscripcionesAbiertasService;
import com.seguridadlimite.util.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/permisos")
@RequiredArgsConstructor
public class PermisoTrabajoAlturasInscripcionesAbiertasController {

    private final VerificarInscripcionesAbiertasService verificarInscripcionesAbiertasUseCase;

    @GetMapping("/inscripciones-abiertas")
    public ResponseEntity<Boolean> verificarInscripcionesAbiertas() {

        boolean hayInscripcionesAbiertas =
                verificarInscripcionesAbiertasUseCase.execute(DateUtil.getCurrentDate());

        return ResponseEntity.ok(hayInscripcionesAbiertas);
    }
}