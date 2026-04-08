package com.seguridadlimite.springboot.backend.apirest.util;

import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class ExistFile {

    public Boolean existFile(String path, String tipo, Long id) {
        String fileName = path + tipo + id + ".png";
        File file = new File(fileName);

        return file.exists() || file.isFile();
    }
}
