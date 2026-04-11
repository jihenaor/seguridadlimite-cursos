package com.seguridadlimite.models.aprendiz.infrastructure.repository;

import com.seguridadlimite.models.aprendiz.domain.Aprendiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AprendizRepository extends JpaRepository<Aprendiz, Integer> {
    
    @Query("SELECT a FROM Aprendiz a JOIN FETCH a.trabajador WHERE a.idPermiso = :idPermiso")
    List<Aprendiz> findByIdPermisoWithTrabajador(@Param("idPermiso") Integer idPermiso);
    
    List<Aprendiz> findByIdPermiso(Integer idPermiso);
}
