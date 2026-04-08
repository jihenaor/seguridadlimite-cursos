package com.seguridadlimite.models.aprendiz.application.extraerCertificado;

import com.seguridadlimite.springboot.backend.apirest.exceptions.BusinessException;

public interface ExtraerCertificadoTrabajadorService {
    String buscar(Long idAprendiz) throws BusinessException;
}
