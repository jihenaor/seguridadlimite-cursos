package com.seguridadlimite.models.chequeo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "GrupoChequeo")
public class GrupoChequeo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_grupo")
    private Integer idGrupo;
    
    @Column(name = "codigo", unique = true, nullable = false)
    private String codigo;
    
    @Column(name = "descripcion", nullable = false)
    private String descripcion;
    
    @Column(name = "posibles_valores", nullable = false)
    private String posiblesValores;
    
    @JsonBackReference
    @OneToMany(mappedBy = "idGrupo", cascade = CascadeType.ALL)
    private List<DetalleChequeo> detalles;
} 