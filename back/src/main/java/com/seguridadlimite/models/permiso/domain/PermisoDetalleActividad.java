package com.seguridadlimite.models.permiso.domain;

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
@Table(name = "permiso_detalle_actividad")
public class PermisoDetalleActividad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_permiso_actividad")
    private Integer idPermisoActividad;

    @Column(name = "actividadrealizar", nullable = false, length = 80)
    private String actividadRealizar;

    @Column(name = "peligros", nullable = false, length = 80)
    private String peligros;

    @Column(name = "controlesrequeridos", nullable = false, length = 80)
    private String controlesRequeridos;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_permiso")
    private PermisoTrabajoAlturas permisoTrabajoAlturas;
}