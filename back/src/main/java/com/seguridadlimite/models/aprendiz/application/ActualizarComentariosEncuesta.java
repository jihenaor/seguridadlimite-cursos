package com.seguridadlimite.models.aprendiz.application;

import com.seguridadlimite.models.aprendiz.domain.Aprendiz;
import com.seguridadlimite.models.aprendiz.infraestructure.IAprendizDao;
import com.seguridadlimite.springboot.backend.apirest.exceptions.BusinessException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ActualizarComentariosEncuesta {

    private final IAprendizDao dao;

    @Transactional
    public void update(long idAprendiz, String comentariosencuesta) throws BusinessException {

        if (comentariosencuesta == null || comentariosencuesta.length() > 400) {
            return;
        }

        Aprendiz aprendiz = dao.findById(idAprendiz).orElseThrow();

        if (aprendiz.getFechaencuesta() != null) {
            return;
        }


        dao.updateComentariosencuesta(comentariosencuesta, idAprendiz);
    }
}
