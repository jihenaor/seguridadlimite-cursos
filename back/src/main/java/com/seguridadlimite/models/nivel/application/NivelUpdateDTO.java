package com.seguridadlimite.models.nivel.application;

import com.seguridadlimite.models.nivel.domain.dto.DiaDto;
import lombok.Data;

import java.util.List;

@Data
public class NivelUpdateDTO {
    private Long id;
    private String fechadesde;
    private String fechahasta;
    private boolean seleccionado;
    private Integer cupoInicial;
    private Integer numerogrupos;
    private Integer dias;
    private List<DiaDto> diasdiseno;
}