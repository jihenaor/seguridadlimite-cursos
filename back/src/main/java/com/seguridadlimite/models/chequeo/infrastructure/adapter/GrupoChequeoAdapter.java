package com.seguridadlimite.models.chequeo.infrastructure.adapter;

import com.seguridadlimite.models.chequeo.domain.GrupoChequeo;
import com.seguridadlimite.models.chequeo.domain.port.GrupoChequeoPort;
import com.seguridadlimite.models.chequeo.infrastructure.repository.GrupoChequeoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class GrupoChequeoAdapter implements GrupoChequeoPort {
    
    private final GrupoChequeoRepository repository;
    
    @Override
    public GrupoChequeo save(GrupoChequeo grupoChequeo) {
        return repository.save(grupoChequeo);
    }
    
    @Override
    public Optional<GrupoChequeo> findById(Integer id) {
        return repository.findById(id);
    }
    
    @Override
    public Optional<GrupoChequeo> findByCodigo(String codigo) {
        return repository.findByCodigo(codigo);
    }
    
    @Override
    public List<GrupoChequeo> findAll() {
        return repository.findAll();
    }
    
    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
} 