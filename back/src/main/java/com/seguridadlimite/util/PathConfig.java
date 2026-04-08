package com.seguridadlimite.util;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "path")
@Data
public class PathConfig {
    private String fotos;
    private String signatures;
    private String certificados;
    private String documents;
}
