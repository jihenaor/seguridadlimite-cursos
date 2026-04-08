package com.seguridadlimite.models.aprendiz.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class FotoDTO {
    private Long id;
    private String base64;
    private String ext;
} 