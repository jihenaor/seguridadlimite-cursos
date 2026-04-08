package com.seguridadlimite.models.aprendiz.application.extraerfirma;

import com.seguridadlimite.models.aprendiz.domain.Aprendiz;
import com.seguridadlimite.springboot.backend.apirest.exceptions.BusinessException;

public interface IExtraerFirmaService {
    String extraerFirma(Long id) throws BusinessException;
}
