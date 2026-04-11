package com.seguridadlimite.models.trabajador.application;

import com.seguridadlimite.models.aprendiz.domain.Aprendiz;
import com.seguridadlimite.models.aprendiz.infraestructure.IAprendizDao;
import com.seguridadlimite.models.dao.ITrabajadorDao;
import com.seguridadlimite.models.trabajador.dominio.Trabajador;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class TrabajadorfindByNumerodocumentoIdGrupoCu {

    private final ITrabajadorDao dao;

    private final IAprendizDao aprendizDao;

    @Transactional(readOnly = true)
    public Trabajador findByNumerodocumento(String numerodocumento, Long idgrupo) {
        Trabajador t;
        Aprendiz aprendiz;

        t = dao.findBynumerodocumento(numerodocumento);
/*
        aprendiz = aprendizDao.findByIdtrabajadorIdgrupo(t.getId(), idgrupo);

        t.setIdaprendiz(aprendiz.getId());
        t.setIdenfasis(aprendiz.getEnfasis().getId() == null ? null : aprendiz.getEnfasis().getId().intValue());
        t.setIdnivel(aprendiz.getNivel().getId() == null ? null : aprendiz.getNivel().getId().intValue());
*/
        return t;
    }
}
