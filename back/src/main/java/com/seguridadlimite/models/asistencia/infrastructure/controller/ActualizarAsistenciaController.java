package com.seguridadlimite.models.asistencia.infrastructure.controller;

import com.seguridadlimite.models.aprendiz.domain.AsistenciaDTO;
import com.seguridadlimite.models.asistencia.domain.port.in.UpdateAsistenciaUseCase;
import com.seguridadlimite.models.asistencia.infrastructure.mapper.AsistenciaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/asistencia")
@RequiredArgsConstructor
public class ActualizarAsistenciaController {

    private final UpdateAsistenciaUseCase updateAsistenciaUseCase;
    private final AsistenciaMapper asistenciaMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> registrarAsistencia(@RequestBody AsistenciaDTO asistenciaDTO) {
        updateAsistenciaUseCase.update(asistenciaMapper.toDomain(asistenciaDTO));
        return ResponseEntity.ok().build();
    }
} 