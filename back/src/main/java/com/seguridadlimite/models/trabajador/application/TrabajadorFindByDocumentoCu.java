package com.seguridadlimite.models.trabajador.application;

import com.seguridadlimite.models.dao.ITrabajadorDao;
import com.seguridadlimite.models.trabajador.dominio.Trabajador;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class TrabajadorFindByDocumentoCu {

    private final ITrabajadorDao dao;

    @Transactional(readOnly = true)
    public Trabajador findByNumerodocumento(String numerodocumento) {
        return dao.findBynumerodocumento(numerodocumento);
    }
}
