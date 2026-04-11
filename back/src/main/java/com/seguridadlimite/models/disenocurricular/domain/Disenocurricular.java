package com.seguridadlimite.models.disenocurricular.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "sl_disenocurricular")
@Data
public class Disenocurricular implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;

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
    private short dia;

    @Column(name = "horas", nullable = false, precision = 4, scale = 2)
    @NotNull
    @PositiveOrZero
    private BigDecimal horas;

    @Basic(optional = false)
    @NotNull
    private int idnivel;
}