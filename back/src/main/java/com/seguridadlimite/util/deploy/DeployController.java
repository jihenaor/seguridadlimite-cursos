package com.seguridadlimite.util.deploy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class DeployController {

    // Inyectamos el token secreto definido en application.properties
    @Value("${deploy.secret-token}")
    private String secretDeployToken;

    @PostMapping("/deploy")
    public ResponseEntity<String> deploy(@RequestHeader(value = "X-Deploy-Token", required = false) String deployToken) {

        // Validamos que se haya enviado el token y que sea correcto
        if (deployToken == null || !deployToken.equals(secretDeployToken)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("No autorizado para ejecutar este endpoint.");
        }

        try {
            // Establecemos el directorio de trabajo como el directorio actual (donde se encuentra el deploy.sh)
            ProcessBuilder processBuilder = new ProcessBuilder();
            File workingDir = new File(".."); // directorio actual (target)
            System.out.println("Directorio de trabajo absoluto: " + workingDir.getAbsolutePath());
            processBuilder.directory(workingDir);

            // Ejecutamos el script a través de bash para evitar problemas de permisos
            processBuilder.command("bash", "./deploy.sh");

            Process process = processBuilder.start();

            // Captura la salida del comando (opcional)
            String output = new BufferedReader(new InputStreamReader(process.getInputStream()))
                    .lines()
                    .collect(Collectors.joining("\n"));

            int exitCode = process.waitFor();
            if (exitCode == 0) {
                return ResponseEntity.ok("Despliegue ejecutado con éxito:\n" + output);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("El despliegue falló con código de salida: " + exitCode + "\n" + output);
            }
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al ejecutar el script de despliegue: " + e.getMessage());
        }
    }
}
