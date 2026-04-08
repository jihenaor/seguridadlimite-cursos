package com.seguridadlimite.models.aprendiz.application.updateSignature.application;

import com.seguridadlimite.models.entity.FirmaAprendiz;

import java.io.IOException;

public interface IUpdateSignatureService {
    void saveFirma(FirmaAprendiz f) throws IOException;
}
