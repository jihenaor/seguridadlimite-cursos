package com.seguridadlimite.models.chequeo.domain.port;

import com.seguridadlimite.models.chequeo.domain.GrupoChequeo;

import java.util.List;
import java.util.Optional;

public interface GrupoChequeoPort {
    GrupoChequeo save(GrupoChequeo grupoChequeo);
    Optional<GrupoChequeo> findById(Integer id);
    Optional<GrupoChequeo> findByCodigo(String codigo);
    List<GrupoChequeo> findAll();
    void deleteById(Integer id);
} 