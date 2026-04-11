package com.seguridadlimite.models.aprendiz.application.mapper;

import com.seguridadlimite.models.aprendiz.domain.Aprendiz;
import com.seguridadlimite.models.aprendiz.domain.AprendizEvaluacionDTO;
import com.seguridadlimite.models.aprendiz.domain.AsistenciaDTO;
import com.seguridadlimite.models.aprendiz.domain.DiaDTO;
import com.seguridadlimite.models.aprendiz.domain.ModuloDTO;
import com.seguridadlimite.models.aprendiz.domain.UnidadDTO;
import com.seguridadlimite.models.asistencia.domain.Asistencia;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface AprendizEvaluacionMapper {

    AprendizEvaluacionMapper INSTANCE = Mappers.getMapper(AprendizEvaluacionMapper.class);

    @Mapping(target = "nombreCompletoTrabajador", source = "trabajador.nombrecompleto")
    @Mapping(target = "id", source = "id")
    @Mapping(target = "idtrabajador", source = "trabajador.id")
    @Mapping(target = "numerodocumento", source = "trabajador.numerodocumento")
    @Mapping(target = "nivel", source = "nivel")
    @Mapping(target = "fechaencuesta", source = "fechaencuesta")
    @Mapping(target = "eingreso", source = "eingreso")
    @Mapping(target = "eteorica1", source = "eteorica1")
    @Mapping(target = "eteorica2", source = "eteorica2")
    @Mapping(target = "epractica", source = "epractica")
    @Mapping(target = "pagocurso", source = "pagocurso")
    @Mapping(target = "fechainscripcion", source = "fechainscripcion", dateFormat = "yyyy-MM-dd")
    @Mapping(target = "modulos", expression = "java(mapModulos(aprendiz))")
    @Mapping(target = "foto", source = "trabajador.foto")
    @Mapping(target = "fechareentrenamiento", source = "fechareentrenamiento")
    @Mapping(target = "fechaemision", source = "fechaemision")
    @Mapping(target = "idpermiso", source = "permisoTrabajoAlturas.idPermiso")
    @Mapping(target = "codigoministerio", source = "permisoTrabajoAlturas.codigoministerio")
    AprendizEvaluacionDTO toDto(Aprendiz aprendiz);

    @Mapping(target = "nivel", source = "nivel")
    Aprendiz toEntity(AprendizEvaluacionDTO dto);

    default List<ModuloDTO> mapModulos(Aprendiz aprendiz) {
        if (aprendiz.getModulos() == null) {
            return null;
        }
        return aprendiz.getModulos().stream().map(modulo -> {
            ModuloDTO moduloDTO = new ModuloDTO();
            moduloDTO.setModulo(String.valueOf(modulo.getModulo()));
            moduloDTO.setDias(modulo.getDias().stream().map(dia ->
                    new DiaDTO(String.valueOf(dia.getDia()),
                            dia.getFechaProgramada(),
                            dia.getUnidads().stream().map(unidad -> {

                                Asistencia asistencia = aprendiz.getAsistencias().stream().filter(
                                                asistencia1 -> asistencia1.getModulo().intValue() == modulo.getModulo() &&
                                                        asistencia1.getDia().intValue() == dia.getDia() &&
                                                        asistencia1.getUnidad().equals(unidad.getUnidad()))
                                        .findFirst().get();

                                return new UnidadDTO(
                                        unidad.getUnidad(),
                                        new AsistenciaDTO((long) asistencia.getId(),
                                                (long) asistencia.getIdaprendiz(),
                                                asistencia.getFecha() != null,
                                                asistencia.getFecha()));
                            }).toList())
            ).toList());
            return moduloDTO;

        }).toList();
    }
}
