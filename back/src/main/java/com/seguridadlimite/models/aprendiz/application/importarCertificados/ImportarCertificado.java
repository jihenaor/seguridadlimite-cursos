package com.seguridadlimite.models.aprendiz.application.importarCertificados;

import lombok.extern.slf4j.Slf4j;


import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Service
@Slf4j
public class ImportarCertificado {

    @Value("${path.certificados}")
    private String certificadosPath;

    public void saveFile(String fileName, MultipartFile multipartFile) throws IOException {
        File uploadPath = new File(certificadosPath);

        if (!uploadPath.exists()) {
            uploadPath.mkdirs();
        }

        try (InputStream inputStream = multipartFile.getInputStream()) {
            if (fileName.toLowerCase().endsWith(".zip")) {
                extractZip(certificadosPath, inputStream);
            } else {
                extractPDF(certificadosPath, fileName, inputStream);
            }
        }
    }

    private static void extractPDF(String uploadDir, String fileName, InputStream inputStream) throws IOException {
        // Verificar si el archivo ya existe
        File outputFile = new File(uploadDir + File.separator + fileName);
        if (outputFile.exists()) {
            log.info("El archivo ya existe. No se reemplazará.");
            return;
        }

        // Si no es un archivo ZIP, guardar el archivo normalmente
        String newFileName = fileName;
        if (fileName.contains(" CERT")) {
            newFileName = fileName.replace(" CERT", "");
        }

        try (OutputStream outputStream = new FileOutputStream(uploadDir + File.separator + newFileName)) {
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
                File entryFile = new File(uploadDir, entryFileName);
                if (entry.isDirectory()) {
                    entryFile.mkdirs();
                } else {
                    try (OutputStream outputStream = new FileOutputStream(entryFile)) {
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
