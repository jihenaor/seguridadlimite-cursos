package com.seguridadlimite.models.aprendiz.application.importarCertificados;

import lombok.extern.slf4j.Slf4j;


import com.seguridadlimite.util.StorageDirectories;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Service
@Slf4j
public class ImportarCertificado {

    @Value("${path.certificados}")
    private String certificadosPath;

    public void saveFile(String fileName, MultipartFile multipartFile) throws IOException {
        Files.createDirectories(Path.of(certificadosPath));

        try (InputStream inputStream = multipartFile.getInputStream()) {
            if (fileName.toLowerCase().endsWith(".zip")) {
                extractZip(certificadosPath, inputStream);
            } else {
                extractPDF(certificadosPath, fileName, inputStream);
            }
        }
    }

    private static void extractPDF(String uploadDir, String fileName, InputStream inputStream) throws IOException {
        Path candidatoExistente = StorageDirectories.resolveUnder(uploadDir, fileName);
        if (Files.exists(candidatoExistente)) {
            log.info("El archivo ya existe. No se reemplazará.");
            return;
        }

        String newFileName = fileName;
        if (fileName.contains(" CERT")) {
            newFileName = fileName.replace(" CERT", "");
        }

        Path target = StorageDirectories.resolveUnder(uploadDir, newFileName);
        StorageDirectories.ensureParentExists(target);
        try (OutputStream outputStream = Files.newOutputStream(target)) {
            int read;
            byte[] bytes = new byte[1024];
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
        }
    }


    private static void extractZip(String uploadDir, InputStream inputStream) throws IOException {
        try (ZipInputStream zipInputStream = new ZipInputStream(inputStream)) {
            ZipEntry entry;
            while ((entry = zipInputStream.getNextEntry()) != null) {
                String entryFileName = entry.getName();

                entryFileName = entryFileName.replaceAll("\\s+(?=\\.pdf)", "");
                Path base = Path.of(uploadDir).normalize();
                Path entryPath = base.resolve(entryFileName).normalize();
                if (!entryPath.startsWith(base)) {
                    throw new IOException("Entrada ZIP fuera del directorio destino: " + entryFileName);
                }
                if (entry.isDirectory()) {
                    Files.createDirectories(entryPath);
                } else {
                    StorageDirectories.ensureParentExists(entryPath);
                    try (OutputStream outputStream = Files.newOutputStream(entryPath)) {
                        byte[] buffer = new byte[1024];
                        int length;
                        while ((length = zipInputStream.read(buffer)) > 0) {
                            outputStream.write(buffer, 0, length);
                        }
                    }
                }
                zipInputStream.closeEntry();
            }
        }
    }

}
