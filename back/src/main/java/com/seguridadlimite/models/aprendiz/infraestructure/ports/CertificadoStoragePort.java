package com.seguridadlimite.models.aprendiz.infraestructure.ports;

import org.springframework.web.multipart.MultipartFile;

public interface CertificadoStoragePort {
    void saveCertificado(MultipartFile file, Long idAprendiz);
}