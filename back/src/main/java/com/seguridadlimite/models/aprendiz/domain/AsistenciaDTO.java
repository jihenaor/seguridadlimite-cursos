package com.seguridadlimite.models.aprendiz.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AsistenciaDTO {
    private Long id;
    private Long idaprendiz;
    private Boolean selected;
    private String fecha;
}
