package com.seguridadlimite.models.chequeo.application.port.in;

import com.seguridadlimite.models.chequeo.domain.GrupoChequeo;

import java.util.List;
import java.util.Optional;

public interface ConsultarGrupoChequeoYDetalleUseCase {
    List<GrupoChequeo> consultarTodos();
    Optional<GrupoChequeo> consultarPorId(Integer idGrupo);
    Optional<GrupoChequeo> consultarPorCodigo(String codigo);
} 