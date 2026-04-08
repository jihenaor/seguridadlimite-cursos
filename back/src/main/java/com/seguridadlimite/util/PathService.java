package com.seguridadlimite.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PathService {

    @Value("${path.linux.documents:/root/images/documents/}")
    private String linuxDocumentsPath;

    @Value("${path.windows.documents:C:\\Users\\Usuario\\Documents\\}")
    private String windowsDocumentsPath;

    /**
     * Detecta si el sistema operativo es Windows.
     *
     * @return true si es Windows, false en caso contrario.
     */
    private boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("win");
    }

    /**
     * Devuelve la ruta correcta para los documentos según el sistema operativo.
     *
     * @return Ruta de documentos.
     */
    public String getDocumentsPath() {
        return isWindows() ? windowsDocumentsPath : linuxDocumentsPath;
    }
}
