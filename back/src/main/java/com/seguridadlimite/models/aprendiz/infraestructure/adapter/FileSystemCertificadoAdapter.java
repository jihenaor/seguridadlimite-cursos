package com.seguridadlimite.models.aprendiz.infraestructure.adapter;

import com.seguridadlimite.models.aprendiz.domain.Aprendiz;
import com.seguridadlimite.models.aprendiz.infraestructure.IAprendizDao;
import com.seguridadlimite.models.aprendiz.infraestructure.ports.CertificadoStoragePort;
import com.seguridadlimite.springboot.backend.apirest.exceptions.BusinessException;
import com.seguridadlimite.springboot.backend.apirest.util.GetPathFiles;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Component
@AllArgsConstructor
public class FileSystemCertificadoAdapter implements CertificadoStoragePort {


    private final IAprendizDao aprendizDao;

    private final GetPathFiles getPathFiles;



    @Override
    public void saveCertificado(MultipartFile file, Long idAprendiz) {
        Aprendiz a = aprendizDao.findById(idAprendiz).orElseThrow(() -> new BusinessException("No se encontro aprendiz con el id " + idAprendiz));

        if (a.getCodigoverificacion() == null || a.getCodigoverificacion().isEmpty()) {
            throw new BusinessException("El aprendiz no tiene codigo de verificacion " + idAprendiz);
        }

        try {
            Path uploadPath = Paths.get(getPathFiles.getCertificadosPath());
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String fileName = a.getCodigoverificacion() + ".pdf";
            Path filePath = uploadPath.resolve(fileName);

            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("No se pudo guardar el archivo", e);
        }
    }

    private boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("win");
    }
}