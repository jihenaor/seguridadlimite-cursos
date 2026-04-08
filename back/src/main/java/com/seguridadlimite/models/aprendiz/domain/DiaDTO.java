package com.seguridadlimite.models.aprendiz.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class DiaDTO {
    private String dia;
    private String fechaProgramada;
    private List<UnidadDTO> unidads;
}

