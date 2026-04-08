package com.seguridadlimite.models.nivel.domain;

import lombok.Builder;
import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@Builder
@AllArgsConstructor
public class NivelDTO {
    private Long id;
    private String nombre;
} 