package com.seguridadlimite.models.permiso.infrastructure.adapter;

import com.seguridadlimite.models.permiso.domain.PermisoTrabajoAlturas;
import com.seguridadlimite.models.permiso.domain.port.PermisoTrabajoAlturasPort;
import com.seguridadlimite.models.permiso.infrastructure.repository.PermisoTrabajoAlturasRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PermisoTrabajoAlturasAdapter implements PermisoTrabajoAlturasPort {
    
    private final PermisoTrabajoAlturasRepository repository;
    
    @Override
    public PermisoTrabajoAlturas save(PermisoTrabajoAlturas permisoTrabajoAlturas) {
        return repository.save(permisoTrabajoAlturas);
    }
    
    @Override
    public Optional<PermisoTrabajoAlturas> findById(Integer id) {
        return repository.findById(id);
    }
    
    @Override
    public List<PermisoTrabajoAlturas> findByIdNivel(Integer idNivel) {
        return repository.findByIdNivel(idNivel);
    }

    @Override
    public List<PermisoTrabajoAlturas> findUltimoIngreso() {
        return repository.findTop10ByOrderByIdPermisoDesc();
    }

    @Override
    public List<PermisoTrabajoAlturas> findByFechaInicioBetween(String fechaInicio, String fechaFinal) {
        return repository.findByFechaInicioBetweenOrderByIdPermisoDesc(fechaInicio, fechaFinal);
    }

    @Override
    public List<PermisoTrabajoAlturas> findPermisosVigentesEnFecha(String fecha, long idnivel) {
        return repository.findAvailablePermissionsByDate(fecha, idnivel);
    }

    @Override
    public List<PermisoTrabajoAlturas> findPermisosVigentesEnFechaIdNivel(String fecha, long idnivel) {
        return repository.findByDateIdNivel(fecha, idnivel);
    }

    @Override
    public boolean existenInscripcionesAbiertas(String fecha) {
        return repository.existsByFechaBetweenValidoDesdeAndValidoHasta(fecha);
    }

    @Override
    public boolean existenInscripcionesActivas(String fecha) {
        return repository.existsByFechaBetweenValidoDesdeValidoHasta(fecha);
    }

    @Override
    public boolean existenInscripcionesActivasIdNivel(String fecha, long idNivel) {
        return repository.existsByFechaBetweenValidoDesdeValidoHastaNivel(fecha, idNivel);
    }

    @Override
    public List<PermisoTrabajoAlturas> findPermisosVigentesEnFecha(String fecha) {
        return repository.findByDate(fecha);
    }

    @Override
    public List<PermisoTrabajoAlturas> findPermisosVigentesEnFechaWithPermisoFechas(String fecha) {
        return repository.findByDateWithPermisoFechas(fecha);
    }

    @Override
    public Optional<PermisoTrabajoAlturas> findPermisosFechaInicioIdnivelIdpersonaautoriza(
            String fechaInicio, Integer idNivel, Integer idpersonaautoriza) {
        return repository.findByFechaInicioNivelIdpersonaautoriza(fechaInicio, idNivel, idpersonaautoriza);
    }

    @Override
    public Optional<PermisoTrabajoAlturas> findByCodigoministerio(String codigoministerio) {
        return repository.findByCodigoministerio(codigoministerio);
    }

    @Override
    public List<PermisoTrabajoAlturas> findAll() {
        return repository.findAll();
    }
} 