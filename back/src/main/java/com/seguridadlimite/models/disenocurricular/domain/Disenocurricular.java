package com.seguridadlimite.models.disenocurricular.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

@Entity
@Table(name = "sl_disenocurricular")
@Data
public class Disenocurricular implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Long id;

    @Basic(optional = false)
    @NotNull
    private Integer modulo;

    @Basic(optional = false)
    @NotNull
    private String contexto;

    @Basic(optional = false)
    @NotNull
    private String unidad;

    @Basic(optional = false)
    @NotNull
    private Integer dia;

    @Basic(optional = false)
    @NotNull
    private Double horas;

    @Basic(optional = false)
    @NotNull
    private Long idnivel;
}