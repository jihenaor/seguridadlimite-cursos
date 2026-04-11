package com.seguridadlimite.models.nivel.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DiaDto {
    private short dia;
    private String fecha;
    private boolean seleccionado;
    private String contexto;
    private String unidad;

}
