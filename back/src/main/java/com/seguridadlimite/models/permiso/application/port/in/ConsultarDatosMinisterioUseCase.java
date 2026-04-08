package com.seguridadlimite.models.permiso.application.port.in;

import java.util.List;

import com.seguridadlimite.models.permiso.domain.InformeministerioDto;

public interface ConsultarDatosMinisterioUseCase {
    List<InformeministerioDto> ejecutar(List<Integer> idPermisosTrabajos);
} 