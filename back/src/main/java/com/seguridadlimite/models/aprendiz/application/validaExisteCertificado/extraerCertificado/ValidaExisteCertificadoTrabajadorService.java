package com.seguridadlimite.models.aprendiz.application.validaExisteCertificado.extraerCertificado;

import com.seguridadlimite.springboot.backend.apirest.exceptions.BusinessException;

import java.util.List;

public interface ValidaExisteCertificadoTrabajadorService {
    List<String> buscar() throws BusinessException;
}
