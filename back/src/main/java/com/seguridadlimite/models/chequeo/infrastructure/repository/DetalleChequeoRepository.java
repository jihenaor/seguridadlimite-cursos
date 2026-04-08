package com.seguridadlimite.models.chequeo.infrastructure.repository;

import com.seguridadlimite.models.chequeo.domain.DetalleChequeo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DetalleChequeoRepository extends JpaRepository<DetalleChequeo, Integer> {
    List<DetalleChequeo> findByIdGrupo(Integer idGrupo);
    Optional<DetalleChequeo> findByCodigoAndIdGrupo(String codigo, Integer idGrupo);
} 