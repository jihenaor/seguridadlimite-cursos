package com.seguridadlimite.models.permiso.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.seguridadlimite.models.chequeo.domain.GrupoChequeo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "permiso_detalle_chequeo")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PermisoDetalleChequeo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_permiso_detalle")
    private Integer idPermisoDetalle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_permiso")
    private PermisoTrabajoAlturas permisoTrabajoAlturas;

    @Column(name = "id_grupo")
    private Integer idGrupo;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @Column(name = "respuesta")
    private String respuesta;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_grupo", referencedColumnName = "id_grupo", insertable = false, updatable = false)
    private GrupoChequeo grupoChequeo;
}