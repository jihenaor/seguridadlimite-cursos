package com.seguridadlimite.models.permiso.infrastructure.adapter;

import com.seguridadlimite.models.permiso.domain.PermisoFechas;
import com.seguridadlimite.models.permiso.domain.port.PermisoFechasPort;
import com.seguridadlimite.models.permiso.infrastructure.repository.PermisoFechasRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PermisoFechasAdapter implements PermisoFechasPort {
    
    private final PermisoFechasRepository repository;
    
    @Override
    public PermisoFechas save(PermisoFechas permisoFechas) {
        return repository.save(permisoFechas);
    }
    
    @Override
    public List<PermisoFechas> saveAll(List<PermisoFechas> permisoFechas) {
        return repository.saveAll(permisoFechas);
    }
}
