package com.seguridadlimite.springboot.backend.apirest.util.infraestructure;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;

@Component
@Slf4j
public class FileRepositoryImpl implements FileRepository {

    @Override
    public Optional<byte[]> readFile(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            log.info("El archivo no existe o no es un archivo válido: " + filePath);
            return Optional.empty();
        }

        try (FileInputStream imageInFile = new FileInputStream(file)) {
            byte[] fileData = new byte[(int) file.length()];
            int bytesRead = imageInFile.read(fileData);

            if (bytesRead != fileData.length) {
                throw new IOException("No se pudo leer todo el archivo: " + filePath);
            }

            return Optional.of(fileData);
        } catch (IOException e) {
            log.error("Error al leer el archivo: " + filePath, e);
            throw e;
        }
    }
}
