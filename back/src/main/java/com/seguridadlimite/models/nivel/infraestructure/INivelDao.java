package com.seguridadlimite.models.nivel.infraestructure;

import com.seguridadlimite.models.nivel.domain.Nivel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface INivelDao extends CrudRepository<Nivel, Long>{

    List<Nivel> findByEstado(String estado);

    @Query("SELECT DISTINCT n " +
            "FROM Nivel n " +
            "JOIN PermisoTrabajoAlturas p ON n.id = p.idNivel " +
            "WHERE p.validodesde <= :fechaActual " +
            "AND p.validohasta >= :fechaActual")
    List<Nivel> findInscripcionesAbiertas(String fechaActual);
}
