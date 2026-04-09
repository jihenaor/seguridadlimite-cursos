package com.seguridadlimite.models.permiso.domain;

import com.seguridadlimite.models.nivel.domain.Nivel;
import com.seguridadlimite.models.personal.dominio.Personal;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "permiso_trabajo_alturas")
public class PermisoTrabajoAlturas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_permiso")
    private Integer idPermiso;

    private String codigoministerio;

    private Integer numerogrupos;

    @Column(name = "fecha_inicio", nullable = false, length = 10)
    private String fechaInicio;

    @Column(name = "valido_desde", nullable = false, length = 10)
    private String validodesde;

    @Column(name = "valido_hasta")
    private String validohasta;

    @Column(name = "hora_inicio")
    private String horainicio;

    @Column(name = "hora_final")
    private String horafinal;

    @Column(name = "proyecto_area_seccion")
    private String proyectoAreaSeccion;

    @Column(name = "ubicacion_especifica")
    private String ubicacionEspecifica;

    @Column(name = "descripcion_tarea")
    private String descripcionTarea;

    @Column(name = "herramientas_utilizar")
    private String herramientasUtilizar;

    @Column(name = "altura_trabajo")
    private String alturaTrabajo;

    @Column(name = "id_nivel")
    private Integer idNivel;

    @JoinColumn(name = "id_nivel", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private Nivel nivel;

    @Column(name = "verificacion_seguridad_social")
    private Boolean verificacionSeguridadSocial;

    @Column(name = "certificado_aptitud_medica")
    private Boolean certificadoAptitudMedica;

    @Column(name = "certificado_competencia")
    private Boolean certificadoCompetencia;

    @Column(name = "condicion_salud_trabajador")
    private Boolean condicionSaludTrabajador;

    private Integer idpersonaautoriza1;

    @JoinColumn(name = "idpersonaautoriza1", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private Personal personaautoriza1;

    private Integer idpersonaautoriza2;

    @JoinColumn(name = "idpersonaautoriza2", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private Personal personaautoriza2;

    private Integer idresponsableemeergencias;

    @JoinColumn(name = "idresponsableemeergencias", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private Personal responsableemeergencias;

    @Column(name = "cupoinicial", nullable = false)
    private Integer cupoinicial;

    @Column(name = "cupofinal", nullable = false)
    private Integer cupofinal;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_permiso") // esta columna estará en la tabla hijo
    private List<PermisoTipoTrabajo> tiposTrabajo;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_permiso") // esta columna estará en la tabla hijo
    private List<PermisoDetalleChequeo> permisoDetalleChequeos;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_permiso") // esta columna estará en la tabla hijo
    private List<PermisoDetalleActividad> permisoDetalleActividades;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_permiso") // esta columna estará en la tabla hijo
    private List<PermisoFechas> permisoFechas;

    private Integer dias;
}
