package com.seguridadlimite.models.permiso.domain.mapper;

import com.seguridadlimite.models.chequeo.domain.GrupoChequeo;
import com.seguridadlimite.models.permiso.domain.PermisoDetalleActividad;
import com.seguridadlimite.models.permiso.domain.PermisoDetalleChequeo;
import com.seguridadlimite.models.permiso.domain.PermisoFechas;
import com.seguridadlimite.models.permiso.domain.PermisoTipoTrabajo;
import com.seguridadlimite.models.permiso.domain.PermisoTrabajoAlturas;
import com.seguridadlimite.models.permiso.domain.dto.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class PermisoTrabajoAlturasMapper {
    
    public PermisoTrabajoAlturasDTO toDTO(PermisoTrabajoAlturas entity, boolean incluyeDetalle) {
        PermisoTrabajoAlturasDTO.PermisoTrabajoAlturasDTOBuilder builder = PermisoTrabajoAlturasDTO.builder()
                .idPermiso(entity.getIdPermiso())
                .codigoministerio(entity.getCodigoministerio())
                .fechaInicio(entity.getFechaInicio())
                .validodesde(entity.getValidodesde())
                .validohasta(entity.getValidohasta())
                .horainicio(entity.getHorainicio())
                .horafinal(entity.getHorafinal())

                .proyectoAreaSeccion(entity.getProyectoAreaSeccion())
                .ubicacionEspecifica(entity.getUbicacionEspecifica())
                .descripcionTarea(entity.getDescripcionTarea())
                .herramientasUtilizar(entity.getHerramientasUtilizar())
                .alturaTrabajo(entity.getAlturaTrabajo())
                .idNivel(entity.getIdNivel())
                .nombrenivel(entity.getNivel().getNombre())
                .verificacionSeguridadSocial(entity.getVerificacionSeguridadSocial())
                .certificadoAptitudMedica(entity.getCertificadoAptitudMedica())
                .certificadoCompetencia(entity.getCertificadoCompetencia())
                .condicionSaludTrabajador(entity.getCondicionSaludTrabajador())
                .idpersonaautoriza1(entity.getIdpersonaautoriza1())
                .nombrepersonaautoriza1(entity.getPersonaautoriza1() == null ? "" : entity.getPersonaautoriza1().getNombrecompleto())
                .idpersonaautoriza2(entity.getIdpersonaautoriza2())
                .nombrepersonaautoriza2(entity.getPersonaautoriza2() == null ? "" : entity.getPersonaautoriza2().getNombrecompleto())
                .idresponsableemeergencias(entity.getIdresponsableemeergencias())
                .cupoinicial(entity.getCupoinicial())
                .cupofinal(entity.getCupofinal())
                .numerogrupos(entity.getNumerogrupos())
                .dias(entity.getDias());

        if (incluyeDetalle) {
            List<GrupoChequeoDTO> grupoChequeoDTOs = entity.getPermisoDetalleChequeos().stream()
                    .map(PermisoDetalleChequeo::getGrupoChequeo)
                    .filter(Objects::nonNull)
                    .distinct()
                    .map(this::convertToGrupoChequeoDTO)
                    .toList(); // o .collect(Collectors.toList()) si usas Java 8


            builder.tiposTrabajo(convertToTipoTrabajoDTOs(entity.getTiposTrabajo()))
                   .permisoDetalleChequeos(convertToDetalleChequeoDTOs(entity.getPermisoDetalleChequeos()))
                   .permisoDetalleActividades(convertToDetalleActividadDTOs(entity.getPermisoDetalleActividades()))
                   .permisoFechas(convertToPermisoFechasDTOs(entity.getPermisoFechas()))
                   .grupoChequeo(grupoChequeoDTOs);
        }

        return builder.build();
    }

    private GrupoChequeoDTO convertToGrupoChequeoDTO(GrupoChequeo grupo) {
        if (grupo == null) return null;
        return GrupoChequeoDTO.builder()
                .idGrupo(grupo.getIdGrupo())
                .descripcion(grupo.getDescripcion())
                .build();
    }

    private List<PermisoDetalleChequeoDTO> convertToDetalleChequeoDTOs(List<PermisoDetalleChequeo> detalles) {
        if (detalles == null) return List.of();
        return detalles.stream()
                .map(detalleChequeo -> {
                        return PermisoDetalleChequeoDTO.builder()
                                .idPermisoDetalle(detalleChequeo.getIdPermisoDetalle())
                                .idPermiso(detalleChequeo.getPermisoTrabajoAlturas().getIdPermiso())
                                .idGrupo(detalleChequeo.getIdGrupo())
                                .descripcion(detalleChequeo.getDescripcion())
                                .respuesta(detalleChequeo.getRespuesta())
                                .build();
                })
                .toList();
    }

    private List<PermisoDetalleActividadDTO> convertToDetalleActividadDTOs(List<PermisoDetalleActividad> detalles) {
        if (detalles == null) return List.of();
        return detalles.stream()
                .map(detalle -> {
                        return PermisoDetalleActividadDTO.builder()
                                .idPermisoActividad(detalle.getIdPermisoActividad())
                                .idPermiso(detalle.getPermisoTrabajoAlturas().getIdPermiso())
                                .actividadrealizar(detalle.getActividadRealizar())
                                .controlesrequeridos(detalle.getControlesRequeridos())
                                .peligros(detalle.getPeligros())
                                .build();

                })
                .toList();
    }

    private List<PermisoTipoTrabajoDTO> convertToTipoTrabajoDTOs(List<?> tipos) {
        if (tipos == null) return List.of();
        return tipos.stream()
                .map(tipo -> {
                    if (tipo instanceof PermisoTipoTrabajoDTO) {
                        return (PermisoTipoTrabajoDTO) tipo;
                    }
                    return null;
                })
                .toList();
    }

    private List<PermisoFechasDTO> convertToPermisoFechasDTOs(List<PermisoFechas> fechas) {
        if (fechas == null) return List.of();
        return fechas.stream()
                .map(fecha -> {
                    return PermisoFechasDTO.builder()
                            .id(fecha.getId())
                            .fecha(fecha.getFecha())
                            .dia(fecha.getDia())
                            .build();
                })
                .toList();
    }

    public PermisoTrabajoAlturas toEntity(PermisoTrabajoAlturasDTO dto,
                                          PermisoTrabajoAlturas permisoTrabajoAlturas) {
        permisoTrabajoAlturas.setFechaInicio(dto.getFechaInicio());
        permisoTrabajoAlturas.setValidodesde(dto.getValidodesde());
        permisoTrabajoAlturas.setValidohasta(dto.getValidohasta());
        permisoTrabajoAlturas.setHorainicio(dto.getHorainicio());
        permisoTrabajoAlturas.setHorafinal(dto.getHorafinal());
        permisoTrabajoAlturas.setProyectoAreaSeccion(dto.getProyectoAreaSeccion());
        permisoTrabajoAlturas.setUbicacionEspecifica(dto.getUbicacionEspecifica());
        permisoTrabajoAlturas.setDescripcionTarea(dto.getDescripcionTarea());
        permisoTrabajoAlturas.setHerramientasUtilizar(dto.getHerramientasUtilizar());
        permisoTrabajoAlturas.setAlturaTrabajo(dto.getAlturaTrabajo());
        permisoTrabajoAlturas.setIdNivel(dto.getIdNivel());
        permisoTrabajoAlturas.setVerificacionSeguridadSocial(dto.getVerificacionSeguridadSocial());
        permisoTrabajoAlturas.setCertificadoAptitudMedica(dto.getCertificadoAptitudMedica());
        permisoTrabajoAlturas.setCertificadoCompetencia(dto.getCertificadoCompetencia());
        permisoTrabajoAlturas.setCondicionSaludTrabajador(dto.getCondicionSaludTrabajador());
        permisoTrabajoAlturas.setCodigoministerio(dto.getCodigoministerio());
        permisoTrabajoAlturas.setCupoinicial(dto.getCupoinicial());
        permisoTrabajoAlturas.setCupofinal(dto.getCupofinal());
        permisoTrabajoAlturas.setIdpersonaautoriza1(dto.getIdpersonaautoriza1());
        permisoTrabajoAlturas.setIdpersonaautoriza2(dto.getIdpersonaautoriza2());
        permisoTrabajoAlturas.setIdresponsableemeergencias(dto.getIdresponsableemeergencias());
        permisoTrabajoAlturas.setNumerogrupos(dto.getNumerogrupos() == null ? 1 : dto.getNumerogrupos());
        permisoTrabajoAlturas.setDias(dto.getDias() == null ? 1 : dto.getDias());
        permisoTrabajoAlturas.setCodigoministerio(dto.getCodigoministerio());

        // Inicialización de las listas de detalles

        Map<Long, PermisoDetalleChequeoDTO> dtoMap = dto.getPermisoDetalleChequeos().stream()
                .collect(Collectors.toMap(PermisoDetalleChequeoDTO::getIdPermisoDetalle, Function.identity()));

        permisoTrabajoAlturas.getPermisoDetalleChequeos().forEach(detalle -> {
            PermisoDetalleChequeoDTO dtoDetalle = dtoMap.get(detalle.getIdPermisoDetalle());
            if (dtoDetalle != null) {
                detalle.setRespuesta(dtoDetalle.getRespuesta());
            } else {
                System.out.println("detalle no existe");
            }
        });


        dto.getPermisoDetalleActividades().forEach(permisoDetalleActividadDTO -> {

            Optional<PermisoDetalleActividad> permisoDetalleActividad = permisoTrabajoAlturas.getPermisoDetalleActividades()
                    .stream()
                    .filter(permisoDetalleActividad1 -> {
                        return permisoDetalleActividad1.getIdPermisoActividad() != null && 
                               permisoDetalleActividadDTO.getIdPermisoActividad() != null &&
                               permisoDetalleActividad1.getIdPermisoActividad().equals(permisoDetalleActividadDTO.getIdPermisoActividad());
                    })
                    .findFirst();

            if (permisoDetalleActividad.isPresent()) {
                permisoDetalleActividad.get().setActividadRealizar(permisoDetalleActividadDTO.getActividadrealizar());
                permisoDetalleActividad.get().setPeligros(permisoDetalleActividadDTO.getPeligros());
                permisoDetalleActividad.get().setControlesRequeridos(permisoDetalleActividadDTO.getControlesrequeridos());
            } else {
                PermisoDetalleActividad detalleActividad = new PermisoDetalleActividad();
                detalleActividad.setPermisoTrabajoAlturas(permisoTrabajoAlturas);
                detalleActividad.setActividadRealizar(permisoDetalleActividadDTO.getActividadrealizar());
                detalleActividad.setPeligros(permisoDetalleActividadDTO.getPeligros());
                detalleActividad.setControlesRequeridos(permisoDetalleActividadDTO.getControlesrequeridos());
                permisoTrabajoAlturas.getPermisoDetalleActividades().add(detalleActividad);
            }
        });

        // Manejo de fechas del permiso
        if (dto.getPermisoFechas() != null) {
            // Limpiar la colección existente
            if (permisoTrabajoAlturas.getPermisoFechas() != null) {
                permisoTrabajoAlturas.getPermisoFechas().clear();
            } else {
                permisoTrabajoAlturas.setPermisoFechas(new java.util.ArrayList<>());
            }
            
            // Crear y agregar nuevas fechas
            dto.getPermisoFechas().forEach(fechaDTO -> {
                PermisoFechas permisoFecha = new PermisoFechas();
                permisoFecha.setId(fechaDTO.getId());
                permisoFecha.setFecha(fechaDTO.getFecha());
                permisoFecha.setDia(fechaDTO.getDia());
                permisoFecha.setContexto("X");
                permisoFecha.setUnidad("X");
                // La relación se establecerá en el servicio
                permisoTrabajoAlturas.getPermisoFechas().add(permisoFecha);
            });
        }

        // Manejo de tipos de trabajo
        if (dto.getTiposTrabajo() != null) {
            // Limpiar la colección existente
            if (permisoTrabajoAlturas.getTiposTrabajo() != null) {
                permisoTrabajoAlturas.getTiposTrabajo().clear();
            }
            
            // Crear y agregar nuevos tipos de trabajo
            dto.getTiposTrabajo().forEach(tipoDTO -> {
                PermisoTipoTrabajo tipo = new PermisoTipoTrabajo();
                if (tipoDTO.getIdPermisoTipoTrabajo() != null) {
                    tipo.setId(tipoDTO.getIdPermisoTipoTrabajo().intValue());
                }
                tipo.setTipoTrabajo(tipoDTO.getDescripcion());
                tipo.setPermisoTrabajoAlturas(permisoTrabajoAlturas);
                permisoTrabajoAlturas.getTiposTrabajo().add(tipo);
            });
        }
  
        return permisoTrabajoAlturas;
    }
} 