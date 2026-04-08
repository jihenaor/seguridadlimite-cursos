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
@Table(name = "permiso-fechas", 
       uniqueConstraints = @UniqueConstraint(
           name = "idx_fecha_permiso", 
           columnNames = {"id_permiso", "fecha"}
       ))
public class PermisoFechas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_permiso")
    private PermisoTrabajoAlturas permisoTrabajoAlturas;

    @Column(name = "fecha", nullable = false, length = 10)
    private String fecha;

    private int dia;

    private String contexto;

    private String unidad;
}
