package com.seguridadlimite.models.permiso.infrastructure.controller;

import com.seguridadlimite.models.permiso.application.port.in.ConsultarPermisoTrabajoAlturasUseCase;
import com.seguridadlimite.models.permiso.domain.dto.PermisoTrabajoAlturasDTO;
import com.seguridadlimite.models.permiso.domain.mapper.PermisoTrabajoAlturasMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/permisos")
@AllArgsConstructor
public class ConsultarPermisoTrabajoAlturasController {

    private final ConsultarPermisoTrabajoAlturasUseCase consultarPermisoTrabajoAlturasUseCase;
    private final PermisoTrabajoAlturasMapper mapper;

    @GetMapping("/{idPermiso}/consultar")
    public ResponseEntity<PermisoTrabajoAlturasDTO> exportarPermiso(@PathVariable Integer idPermiso) {
        return ResponseEntity.ok(mapper.toDTO(consultarPermisoTrabajoAlturasUseCase.consultar(idPermiso), true));
    }
} 