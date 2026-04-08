

package com.seguridadlimite.models.aprendiz.application.inscribiraprendiz;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CertificadoInfo {
    private String nombreaprendiz;
    private String numerodocumento;
    private String arl;
    private String nivel;
    private String empresa;
    private String nit;
    private String representantelegal;
    private String fechaemision;
    private String horas;
    private String codigoministerio;
    private String codigoverificacion;
    private String sourcefirma;
    private String fechainicio;
}
