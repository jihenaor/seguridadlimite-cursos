package com.seguridadlimite.springboot.backend.apirest.util.infraestructure;

import java.io.IOException;
import java.util.Optional;

public interface FileRepository {
    Optional<byte[]> readFile(String filePath) throws IOException;
}