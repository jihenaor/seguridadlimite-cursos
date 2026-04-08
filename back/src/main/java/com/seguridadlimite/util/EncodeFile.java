package com.seguridadlimite.util;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

@Component
public class EncodeFile {

    public String encodeFromInputStream(InputStream inputStream) throws IOException {
        byte[] bytes = IOUtils.toByteArray(inputStream);
        return Base64.getEncoder().encodeToString(bytes);
    }
}
