package com.seguridadlimite.springboot.backend.apirest.util;

import com.seguridadlimite.springboot.backend.apirest.exceptions.BusinessException;
import com.seguridadlimite.springboot.backend.apirest.util.infraestructure.FileRepository;
import com.seguridadlimite.util.PathConfig;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Data
public class GetPathFiles {
    private String fotosPath;
    private String signaturesPath;
    private String certificadosPath;
    private String documentsPath;

    private final FileRepository fileRepository;
    private final PathConfig pathConfig;

    public GetPathFiles(FileRepository fileRepository, PathConfig pathConfig) {
        this.fileRepository = fileRepository;
        this.pathConfig = pathConfig;
    }

    @PostConstruct
    private void initializePaths() {
        if (pathConfig.getFotos() == null || pathConfig.getSignatures() == null || pathConfig.getCertificados() == null) {
            throw new BusinessException("Los valores de los paths son null en " + this.getClass().getSimpleName());
        }

        if (isWindows()) {
            this.fotosPath = "C:\\Users\\Usuario\\Documents\\";
            this.signaturesPath = "C:\\Users\\Usuario\\Documents\\";
            this.certificadosPath = "C:\\Users\\Usuario\\Documents\\";
            this.documentsPath = "C:\\Users\\Usuario\\Documents\\";
        } else {
            this.fotosPath = pathConfig.getFotos();
            this.signaturesPath = pathConfig.getSignatures();
            this.certificadosPath = pathConfig.getCertificados();
            this.documentsPath = pathConfig.getDocuments();
        }
    }

    private boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("win");
    }

}