package com.seguridadlimite.models.permiso.infrastructure.controller;

import com.seguridadlimite.models.nivel.application.NivelUpdateDTO;
import com.seguridadlimite.models.permiso.domain.port.RegistrarPermisosTrabajoPort;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/permisos")
@AllArgsConstructor
public class StartWorkPermissionsController {

    private final RegistrarPermisosTrabajoPort registrarPermisosTrabajoPort;

    @PostMapping("/start-work-permissions")
    public ResponseEntity<Map<String, String>> updateFechas(
            @RequestBody List<NivelUpdateDTO> nivelesDTO,
            @RequestParam(name = "forzarSolapamiento", defaultValue = "false") boolean forzarSolapamiento) {
        registrarPermisosTrabajoPort.registrarPermisos(nivelesDTO, forzarSolapamiento);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Permisos registrados correctamente");

        return ResponseEntity.ok(response);
    }
}
