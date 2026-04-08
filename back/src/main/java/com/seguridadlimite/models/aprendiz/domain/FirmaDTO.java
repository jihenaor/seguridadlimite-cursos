package com.seguridadlimite.models.aprendiz.domain;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class FirmaDTO {
    private Long id;
    private String base64;
    private String ext;
}
