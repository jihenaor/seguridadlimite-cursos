package com.seguridadlimite.models.permiso.infrastructure.controller;

import com.seguridadlimite.models.permiso.application.port.in.ConsultarDatosMinisterioUseCase;
import com.seguridadlimite.models.permiso.domain.InformeministerioDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/findministerio")
@AllArgsConstructor
public class ConsultarDatosMinisterioController {

    private final ConsultarDatosMinisterioUseCase consultarDatosMinisterioUseCase;

    @GetMapping("/consultar")
    public ResponseEntity<List<InformeministerioDto>> consultarDatosMinisterio(@RequestParam List<Integer> idPermisosTrabajos) {
        List<InformeministerioDto> informes = consultarDatosMinisterioUseCase.ejecutar(idPermisosTrabajos);
        return ResponseEntity.ok(informes);
    }
}