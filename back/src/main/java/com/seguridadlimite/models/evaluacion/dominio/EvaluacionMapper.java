package com.seguridadlimite.models.evaluacion.dominio;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import com.seguridadlimite.models.pregunta.domain.PreguntaMapper;

@Mapper(
    componentModel = "spring",
    uses = {PreguntaMapper.class},
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface EvaluacionMapper {
    EvaluacionDTO toDto(Evaluacion evaluacion);
    Evaluacion toEntity(EvaluacionDTO dto);
} 