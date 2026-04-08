package com.seguridadlimite.models.aprendiz.application.inscribiraprendiz;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PermisotrabajoalturasAprendicesInfo {

    private final String nombrecompleto;

    private final String tiponumerodocumento;

    private final String firma;
}
