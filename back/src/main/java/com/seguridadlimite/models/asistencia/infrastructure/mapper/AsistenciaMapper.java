package com.seguridadlimite.models.asistencia.infrastructure.mapper;

import com.seguridadlimite.models.aprendiz.domain.AsistenciaDTO;
import com.seguridadlimite.models.asistencia.domain.Asistencia;
import org.springframework.stereotype.Component;

@Component
public class AsistenciaMapper {
    
    public Asistencia toDomain(AsistenciaDTO dto) {
        return Asistencia.builder()
                .id(dto.getId() != null ? dto.getId().intValue() : 0)
                .idaprendiz(dto.getIdaprendiz() != null ? dto.getIdaprendiz().intValue() : 0)
                .fecha(dto.getFecha())
                .selected(dto.getSelected())
                .build();
    }
} 