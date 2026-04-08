package com.seguridadlimite.models.pregunta.domain;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface PreguntaMapper {
    PreguntaDTO toDto(Pregunta pregunta);
    Pregunta toEntity(PreguntaDTO dto);
} 