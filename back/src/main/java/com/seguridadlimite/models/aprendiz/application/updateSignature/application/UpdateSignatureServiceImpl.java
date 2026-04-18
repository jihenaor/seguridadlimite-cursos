package com.seguridadlimite.models.aprendiz.application.updateSignature.application;

import com.seguridadlimite.models.entity.FirmaAprendiz;
import com.seguridadlimite.springboot.backend.apirest.exceptions.BusinessException;
import com.seguridadlimite.springboot.backend.apirest.util.GetPathFiles;
import com.seguridadlimite.util.StorageDirectories;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;
import java.util.Base64;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
@Slf4j
public class UpdateSignatureServiceImpl implements IUpdateSignatureService {

	private final GetPathFiles getPathFiles;

    @Override
    public void saveFirma(FirmaAprendiz f) throws IOException {
        log.info("Iniciando guardado de firma para aprendiz ID: {}", f.getId());

        // Validar que el objeto no sea null
        if (f == null) {
            log.error("FirmaAprendiz es null");
            throw new BusinessException("Datos de firma requeridos");
        }

        // Validar que el base64 no esté vacío
        if (f.getBase64() == null || f.getBase64().trim().isEmpty()) {
            log.error("Base64 está vacío para aprendiz ID: {}", f.getId());
            throw new BusinessException("Base64 de firma requerido");
        }

        // Limpiar el prefijo data:image si existe
        int poscoma = f.getBase64().indexOf("base64,");
        if (poscoma > 0) {
            f.setBase64(f.getBase64().substring(poscoma + 7));
        }

        // Validar formato base64
        if (!isValidBase64(f.getBase64())) {
            log.error("Formato base64 inválido para aprendiz ID: {}", f.getId());
            throw new BusinessException("Formato base64 inválido");
        }

        try {
            byte[] decodedString = Base64.getDecoder().decode(f.getBase64().getBytes(StandardCharsets.UTF_8));

            // Validar que el contenido decodificado no esté vacío
            if (decodedString.length == 0) {
                log.error("Contenido base64 vacío después de decodificar para aprendiz ID: {}", f.getId());
                throw new BusinessException("Contenido de firma vacío");
            }

            Path firmaPath = StorageDirectories.resolveUnder(
                    getPathFiles.getSignaturesPath(), "S" + f.getId() + ".png");
            StorageDirectories.writeBytes(firmaPath, decodedString);

            log.info("Firma guardada exitosamente para aprendiz ID: {}", f.getId());

        } catch (IllegalArgumentException e) {
            log.error("Error al decodificar base64 para aprendiz ID: {}", f.getId(), e);
            throw new BusinessException("Error al procesar base64: " + e.getMessage());
        } catch (AccessDeniedException e) {
            log.error(
                    "Sin permiso de escritura en firmas (ruta base={}): {}",
                    getPathFiles.getSignaturesPath(),
                    e.getMessage());
            throw new BusinessException(
                    "No se puede guardar la firma: el directorio de almacenamiento no permite escritura. "
                            + "En Docker, el volumen bajo /app/storage debe pertenecer al usuario del proceso (p. ej. "
                            + "appuser) o ejecutar el entrypoint que crea y hace chown de /app/storage.");
        } catch (IOException e) {
            log.error("Error al escribir archivo de firma para aprendiz ID: {}", f.getId(), e);
            throw new BusinessException("No se pudo guardar el archivo de firma: " + e.getMessage());
        }
    }

    // Método auxiliar para validar base64
    private boolean isValidBase64(String base64String) {
        try {
            // Patrón para validar formato base64
            Pattern base64Pattern = Pattern.compile("^[A-Za-z0-9+/]*={0,2}$");

            // Verificar que coincida con el patrón
            if (!base64Pattern.matcher(base64String).matches()) {
                return false;
            }

            // Verificar longitud mínima
            if (base64String.length() < 4) {
                return false;
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

