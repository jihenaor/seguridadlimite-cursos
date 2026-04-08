package com.seguridadlimite.models.grupopregunta.domain;

import com.seguridadlimite.models.evaluacion.dominio.EvaluacionMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    uses = {EvaluacionMapper.class},
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface GrupopreguntaMapper {
    GrupopreguntaDTO toDto(Grupopregunta grupopregunta);
    Grupopregunta toEntity(GrupopreguntaDTO dto);
} 