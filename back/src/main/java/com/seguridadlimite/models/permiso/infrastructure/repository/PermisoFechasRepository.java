package com.seguridadlimite.models.permiso.infrastructure.repository;

import com.seguridadlimite.models.permiso.domain.PermisoFechas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermisoFechasRepository extends JpaRepository<PermisoFechas, Integer> {

}
