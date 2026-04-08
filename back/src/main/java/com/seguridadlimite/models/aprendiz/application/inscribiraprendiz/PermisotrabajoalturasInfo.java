package com.seguridadlimite.models.aprendiz.application.inscribiraprendiz;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PermisotrabajoalturasInfo {
    private Integer idPermiso;
    private String nombreapellidoentrenador;
    private String documentoentrenador;
    private String dia;
    private String mes;
    private String anio;
    private String validodesde;
    private String validohasta;
    private String horaInicio;
    private String horaFinal;
    private String proyectoAreaSeccion;
    private String ubicacionEspecifica;
    private String descripcionTarea;
    private String herramientasUtilizar;
    private String alturaTrabajo;
    private Long idNivel;
    private String verificacionSeguridadSocial;
    private String certificadoAptitudMedica;
    private String certificadoCompetencia;
    private String condicionSaludTrabajador;
    private String personaAutoriza;
    private String nroCcAutoriza;
    private String responsableEmergencias;
    private String nroCcResponsable;
    private String coordinadorAlturas;
    private String nroCcCoordinador;
    private String fachada;
    private String estructura;
    private String poste;
    private String reticula;
    private String cubierta;
    private String suspension;
    private String andamios;
    private String torre;
    private String logoseguridad;
    private String codigoministerio;
    private Integer cupoinicial;
    private Integer cupofinal;
    private List<PermisoDetalleChequeoInfo> detalleselementosproteccion;
    private List<PermisoDetalleChequeoInfo> verificacionpreoperacional;
    private List<PermisoDetalleChequeoInfo> detalleslistaverificacion;
    private List<PermisoDetalleChequeoInfo> detallesanalisistarea;
    private List<PermisoDetalleActividadInfo> detallesactividad;
    private List<PermisotrabajoalturasAprendicesInfo> aprendices;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PermisoDetalleChequeoInfo {
        private String descripcion;
        private String respuesta;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PermisoDetalleActividadInfo {
        private String actividadrealizar;
        private String peligros;
        private String controlesrequeridos;
    }
}
