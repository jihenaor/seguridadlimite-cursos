package com.seguridadlimite.springboot.backend.apirest.util;

import com.seguridadlimite.springboot.backend.apirest.exceptions.BusinessException;
import com.seguridadlimite.springboot.backend.apirest.util.infraestructure.FileRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.Optional;

@Component
@Slf4j
@AllArgsConstructor
public class EncodeFileToBase64 {


    private final FileRepository fileRepository;
    private final GetPathFiles getPathFiles;


    public String encode(String fileName) {
        Optional<byte[]> fileData;
        try {
            fileData = fileRepository.readFile(fileName);
            if (fileData.isEmpty()) {
                throw new BusinessException("El archivo no se pudo leer o no existe: " + fileName);
            }
        } catch (IOException ioException) {
            throw new RuntimeException("El archivo no se pudo leer o no existe: " + fileName);
        }

        byte[] data = fileData.get();
        return Base64.getEncoder().encodeToString(data);
    }

    public Optional<String> encode(String tipo, String id) {
        String fileName = switch (tipo) {
            case "F" -> resolveFotoFilePath(id);
            case "S" -> getPathFiles.getSignaturesPath() + tipo + id + ".png";
            case "C" -> getPathFiles.getCertificadosPath() + id + ".pdf";
            default -> "";
        };

        if (fileName.isEmpty()) {
            return Optional.empty();
        }

        try {
            Optional<byte[]> fileData = fileRepository.readFile(fileName);
            if (fileData.isEmpty()) {
                log.info("No se encontró el archivo Tipo: {} Id:{} Nombre de archivo:{}", tipo, id, fileName);
                return Optional.empty();
            }

            byte[] data = fileData.get();
            return Optional.of(Base64.getEncoder().encodeToString(data));
        } catch (IOException ioException) {
            log.error("Error al leer el archivo: " + fileName, ioException);
            return Optional.empty();
        }
    }

    /** Prioriza JPEG (nuevo flujo); mantiene compatibilidad con PNG histórico. */
    private String resolveFotoFilePath(String id) {
        String base = getPathFiles.getFotosPath() + "F" + id;
        for (String ext : new String[] { "jpg", "jpeg", "png" }) {
            String candidate = base + "." + ext;
            if (Files.isRegularFile(Path.of(candidate))) {
                return candidate;
            }
        }
        return base + ".jpg";
    }
}