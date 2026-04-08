package com.seguridadlimite.models.permiso.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "permiso_tipo_trabajo")
public class PermisoTipoTrabajo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_permiso")
    private PermisoTrabajoAlturas permisoTrabajoAlturas;

    @Column(name = "tipo_trabajo", length = 100, nullable = false)
    private String tipoTrabajo;

}
