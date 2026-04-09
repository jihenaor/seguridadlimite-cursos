package com.seguridadlimite.models.permiso.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PermisoTrabajoAlturasDTO {
    private Integer idPermiso;
    private String fechaInicio;
    private String validodesde;
    private String validohasta;
    private String horainicio;
    private String horafinal;
    private String proyectoAreaSeccion;
    private String ubicacionEspecifica;
    private String descripcionTarea;
    private String herramientasUtilizar;
    private String alturaTrabajo;
    private Integer idNivel;
    private String nombrenivel;
    private Boolean verificacionSeguridadSocial;
    private Boolean certificadoAptitudMedica;
    private Boolean certificadoCompetencia;
    private Boolean condicionSaludTrabajador;

    private String codigoministerio;

    private Integer cupoinicial;

    private Integer cupofinal;

    private Integer idpersonaautoriza1;
    private String nombrepersonaautoriza1;

    private Integer idpersonaautoriza2;
    private String nombrepersonaautoriza2;

    private Integer numerogrupos;

    private Integer dias;

    private Integer idresponsableemeergencias;

    private List<GrupoChequeoDTO> grupoChequeo;
    private List<PermisoTipoTrabajoDTO> tiposTrabajo;
    private List<PermisoDetalleChequeoDTO> permisoDetalleChequeos;
    private List<PermisoDetalleActividadDTO> permisoDetalleActividades;
    private List<PermisoFechasDTO> permisoFechas;
    
} 