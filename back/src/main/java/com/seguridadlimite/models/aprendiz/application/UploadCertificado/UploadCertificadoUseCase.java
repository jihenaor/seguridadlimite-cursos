package com.seguridadlimite.models.aprendiz.application.UploadCertificado;

import org.springframework.web.multipart.MultipartFile;

public interface UploadCertificadoUseCase {
    void uploadCertificado(MultipartFile file, Long idAprendiz);
}