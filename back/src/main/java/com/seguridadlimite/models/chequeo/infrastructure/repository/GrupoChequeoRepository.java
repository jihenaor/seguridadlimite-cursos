package com.seguridadlimite.models.chequeo.infrastructure.repository;

import com.seguridadlimite.models.chequeo.domain.GrupoChequeo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GrupoChequeoRepository extends JpaRepository<GrupoChequeo, Integer> {
    Optional<GrupoChequeo> findByCodigo(String codigo);
} 