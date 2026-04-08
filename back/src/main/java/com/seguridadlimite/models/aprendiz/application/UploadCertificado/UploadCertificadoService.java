package com.seguridadlimite.models.aprendiz.application.UploadCertificado;

import com.seguridadlimite.models.aprendiz.infraestructure.ports.CertificadoStoragePort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class UploadCertificadoService implements UploadCertificadoUseCase {
    private final CertificadoStoragePort certificadoStoragePort;

    public UploadCertificadoService(CertificadoStoragePort certificadoStoragePort) {
        this.certificadoStoragePort = certificadoStoragePort;
    }

    @Override
    public void uploadCertificado(MultipartFile file, Long idAprendiz) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("El archivo está vacío");
        }
        certificadoStoragePort.saveCertificado(file, idAprendiz);
    }
}