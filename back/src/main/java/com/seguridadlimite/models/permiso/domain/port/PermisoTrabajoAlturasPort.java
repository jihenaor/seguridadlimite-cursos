package com.seguridadlimite.models.permiso.domain.port;

import com.seguridadlimite.models.permiso.domain.PermisoTrabajoAlturas;

import java.util.List;
import java.util.Optional;

public interface PermisoTrabajoAlturasPort {
    PermisoTrabajoAlturas save(PermisoTrabajoAlturas permisoTrabajoAlturas);
    Optional<PermisoTrabajoAlturas> findById(Integer id);
    List<PermisoTrabajoAlturas> findByIdNivel(Integer idNivel);
    List<PermisoTrabajoAlturas> findUltimoIngreso();
    List<PermisoTrabajoAlturas> findByFechaInicioBetween(String fechaInicio, String fechaFinal);
    List<PermisoTrabajoAlturas> findPermisosVigentesEnFecha(String fecha, long idnivel);
    List<PermisoTrabajoAlturas> findPermisosVigentesEnFechaIdNivel(String fecha, long idnivel);
    boolean existenInscripcionesAbiertas(String fecha);
    boolean existenInscripcionesActivas(String fecha);
    boolean existenInscripcionesActivasIdNivel(String fecha, long idNivel);
    List<PermisoTrabajoAlturas> findPermisosVigentesEnFecha(String fecha);
    List<PermisoTrabajoAlturas> findPermisosVigentesEnFechaWithPermisoFechas(String fecha);
    Optional<PermisoTrabajoAlturas> findPermisosFechaInicioIdnivelIdpersonaautoriza(String fechaInicio, Long idNivel, Integer idpersonaautoriza);
    Optional<PermisoTrabajoAlturas> findByCodigoministerio(String codigoministerio);
    List<PermisoTrabajoAlturas> findAll();
}