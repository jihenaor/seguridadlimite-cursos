package com.seguridadlimite.models.aprendiz.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ModuloDTO {
    private String modulo;
    private List<DiaDTO> dias;
}
