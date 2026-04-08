package com.seguridadlimite.models.chequeo.application.service;

import com.seguridadlimite.models.chequeo.application.port.in.ConsultarGrupoChequeoYDetalleUseCase;
import com.seguridadlimite.models.chequeo.domain.GrupoChequeo;
import com.seguridadlimite.models.chequeo.domain.port.GrupoChequeoPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ConsultarGrupoChequeoYDetalleService implements ConsultarGrupoChequeoYDetalleUseCase {
    
    private final GrupoChequeoPort grupoChequeoPort;
    
    @Override
    @Transactional(readOnly = true)
    public List<GrupoChequeo> consultarTodos() {
        return grupoChequeoPort.findAll();
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<GrupoChequeo> consultarPorId(Integer idGrupo) {
        return grupoChequeoPort.findById(idGrupo);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<GrupoChequeo> consultarPorCodigo(String codigo) {
        return grupoChequeoPort.findByCodigo(codigo);
    }
} 