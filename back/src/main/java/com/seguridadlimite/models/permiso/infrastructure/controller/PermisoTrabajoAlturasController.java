package com.seguridadlimite.models.permiso.infrastructure.controller;

import com.seguridadlimite.models.permiso.application.port.in.ConsultarPermisoTrabajoAlturasUseCase;
import com.seguridadlimite.models.permiso.application.port.in.ConsultarPermisosTrabajoAlturasPorFechaUseCase;
import com.seguridadlimite.models.permiso.application.port.in.ConsultarPermisosTrabajoAlturasPorFechasUseCase;
import com.seguridadlimite.models.permiso.application.port.in.RegistrarPermisoTrabajoAlturasUseCase;
import com.seguridadlimite.models.permiso.domain.PermisoTrabajoAlturas;
import com.seguridadlimite.models.permiso.domain.dto.PermisoTrabajoAlturasDTO;
import com.seguridadlimite.models.permiso.domain.mapper.PermisoTrabajoAlturasMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/permisos-trabajo-alturas")
@AllArgsConstructor
public class PermisoTrabajoAlturasController {

    private final ConsultarPermisosTrabajoAlturasPorFechasUseCase consultarPermisosUseCase;
    private final RegistrarPermisoTrabajoAlturasUseCase registrarPermisoUseCase;
    private final ConsultarPermisoTrabajoAlturasUseCase consultarPermisoTrabajoAlturasUseCase;
    private final ConsultarPermisosTrabajoAlturasPorFechaUseCase consultarPermisosTrabajoAlturasPorFechaUseCase;

    private final PermisoTrabajoAlturasMapper mapper;

    @GetMapping("/por-fechas")
    public ResponseEntity<List<PermisoTrabajoAlturasDTO>> consultarPorFechas(
            @RequestParam String fechaInicio,
            @RequestParam String fechaFin) {
        List<PermisoTrabajoAlturas> permisos = consultarPermisosUseCase.ejecutar(
                fechaInicio, fechaFin);
        return ResponseEntity.ok(permisos.stream().map(permiso -> mapper.toDTO(permiso, false)).toList());
    }

    @GetMapping("/por-codigo-ministerio")
    public ResponseEntity<List<PermisoTrabajoAlturasDTO>> consultarPorCodigoministerio(
            @RequestParam String codigoministerio) {
        List<PermisoTrabajoAlturas> permisos = consultarPermisosUseCase.ejecutarPorCodigoMinisterio(codigoministerio);
        return ResponseEntity.ok(permisos.stream().map(permiso -> mapper.toDTO(permiso, false)).toList());
    }

    @GetMapping("/por-fecha")
    public ResponseEntity<List<PermisoTrabajoAlturasDTO>> consultarPorFecha(
            @RequestParam String fecha) {
        List<PermisoTrabajoAlturas> permisos = consultarPermisosTrabajoAlturasPorFechaUseCase.ejecutar(fecha);
        return ResponseEntity.ok(permisos.stream().map(permiso -> mapper.toDTO(permiso, true)).toList());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, String>> registrarPermiso(
            @PathVariable Integer id,
            @RequestBody PermisoTrabajoAlturasDTO permisoTrabajoAlturasDTO) {
        permisoTrabajoAlturasDTO.setIdPermiso(id);
        PermisoTrabajoAlturas permisoTrabajoAlturas = consultarPermisoTrabajoAlturasUseCase.consultar(id);
        registrarPermisoUseCase.ejecutar(mapper.toEntity(permisoTrabajoAlturasDTO, permisoTrabajoAlturas));
        return ResponseEntity.ok()
                .body(Map.of("message", "Registro exitoso"));
    }

    @PutMapping("/{id}/fechas-activo")
    public ResponseEntity<Map<String, String>> actualizarFechasActivo(
            @PathVariable Integer id,
            @RequestBody PermisoTrabajoAlturasDTO permisoTrabajoAlturasDTO) {
        PermisoTrabajoAlturas permisoTrabajoAlturas = consultarPermisoTrabajoAlturasUseCase.consultar(id);
        permisoTrabajoAlturas.setValidodesde(permisoTrabajoAlturasDTO.getValidodesde());
        permisoTrabajoAlturas.setValidohasta(permisoTrabajoAlturasDTO.getValidohasta());
        registrarPermisoUseCase.ejecutar(permisoTrabajoAlturas);
        return ResponseEntity.ok()
                .body(Map.of("message", "Fechas de activación actualizadas exitosamente"));
    }

} 