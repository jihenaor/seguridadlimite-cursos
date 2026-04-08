package com.seguridadlimite.models.aprendiz.domain;


import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrabajadorDTO {
    private long id;
    private String tipodocumento;
    private String documento;
    private String primernombre;
    private String segundonombre;
    private String primerapellido;
    private String segundoapellido;
    private String genero;
    private String paisnacimiento;
    private String fechanacimiento;
}
