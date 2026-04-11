package com.seguridadlimite.models.aprendiz.application.buscarpordocumentoparainscripcion;

import lombok.RequiredArgsConstructor;

import com.seguridadlimite.models.aprendiz.domain.Aprendiz;
import com.seguridadlimite.models.aprendiz.infraestructure.IAprendizDao;
import com.seguridadlimite.springboot.backend.apirest.exceptions.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FindByNumerodocumentoParaInscripcion {
    private final IAprendizDao aprendizDao;

    @Transactional(readOnly = true)
    public Aprendiz find(String numerodocumento) throws BusinessException {
        return aprendizDao.findApredizDocumentoFechaLimiteInscripcion(numerodocumento)
                .stream()
                .findFirst()
                .orElse(null);
    }
}
