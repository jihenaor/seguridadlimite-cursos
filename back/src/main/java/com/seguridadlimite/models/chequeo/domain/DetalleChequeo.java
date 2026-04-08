package com.seguridadlimite.models.chequeo.domain;

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
@Table(name = "DetalleChequeo")
public class DetalleChequeo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle")
    private Integer idDetalle;
    
    @Column(name = "id_grupo")
    private Integer idGrupo;
    
    @Column(name = "codigo", nullable = false)
    private String codigo;
    
    @Column(name = "descripcion", nullable = false)
    private String descripcion;
} 