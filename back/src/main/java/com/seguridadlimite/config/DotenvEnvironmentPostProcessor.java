package com.seguridadlimite.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Carga {@code .env} en el {@link org.springframework.core.env.Environment} antes de resolver
 * placeholders de {@code application.yml} (p. ej. {@code ${DB_PASSWORD}}).
 * <p>
 * Busca el archivo en {@code user.dir}, {@code user.dir/back}, y subiendo directorios
 * (útil si el IDE tiene como raíz {@code seguridad/} y el .env está en {@code back/.env}).
 * Las claves presentes en {@code .env} se registran en una fuente <em>antes</em> de
 * {@code systemEnvironment}: ganan sobre variables de entorno del SO con el mismo nombre
 * (evita 1045 en local cuando Windows tiene un {@code DB_PASSWORD} viejo distinto al del archivo).
 * Las propiedades de sistema ({@code -D}) y otras fuentes con mayor prioridad en Boot siguen pudiendo
 * imponerse según el orden estándar del {@code Environment}.
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
public class DotenvEnvironmentPostProcessor implements EnvironmentPostProcessor, Ordered {

    private static final Logger log = LoggerFactory.getLogger(DotenvEnvironmentPostProcessor.class);
    static final String PROPERTY_SOURCE_NAME = "dotenvLocal";

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        Path envFile = resolveEnvFilePath();
        if (envFile == null || !Files.isRegularFile(envFile)) {
            log.debug("No se encontró archivo .env (user.dir={})", System.getProperty("user.dir"));
            return;
        }
        try {
            Dotenv dotenv = Dotenv.configure()
                    .directory(envFile.getParent().toString())
                    .filename(envFile.getFileName().toString())
                    .ignoreIfMalformed()
                    .ignoreIfMissing()
                    .load();

            Map<String, Object> map = new LinkedHashMap<>();
            dotenv.entries().forEach(entry -> {
                String key = entry.getKey();
                if (key == null || key.isBlank()) {
                    return;
                }
                String fromOs = System.getenv(key);
                if (fromOs != null && !fromOs.isBlank() && "DB_PASSWORD".equals(key)) {
                    log.warn(
                            "DB_PASSWORD también está definida en el sistema operativo; se usará la de {} ({} caracteres). "
                                    + "Si MySQL daba 1045, suele ser una clave vieja en variables de entorno de Windows.",
                            envFile.getFileName(),
                            entry.getValue() != null ? entry.getValue().length() : 0);
                }
                map.put(key, entry.getValue());
            });
            if (map.isEmpty()) {
                return;
            }

            MutablePropertySources sources = environment.getPropertySources();
            sources.remove(PROPERTY_SOURCE_NAME);
            // Antes de systemEnvironment: cada clave del .env gana a la variable de entorno del mismo nombre.
            if (sources.contains("systemEnvironment")) {
                sources.addBefore("systemEnvironment", new MapPropertySource(PROPERTY_SOURCE_NAME, map));
            } else {
                sources.addFirst(new MapPropertySource(PROPERTY_SOURCE_NAME, map));
            }
            log.info("Cargadas {} variables desde {}", map.size(), envFile.toAbsolutePath().normalize());
            if (map.containsKey("DB_PASSWORD")) {
                Object pwd = map.get("DB_PASSWORD");
                log.info("DB_PASSWORD resuelta desde .env ({} caracteres).", pwd != null ? pwd.toString().length() : 0);
            }
        } catch (Exception e) {
            log.warn("No se pudo cargar .env en {}: {}", envFile, e.getMessage());
        }
    }

    static Path resolveEnvFilePath() {
        String userDir = System.getProperty("user.dir");
        if (userDir == null || userDir.isBlank()) {
            return null;
        }
        Path base = Paths.get(userDir).toAbsolutePath().normalize();

        Path[] direct = new Path[] {
                base.resolve(".env"),
                base.resolve("back").resolve(".env")
        };
        for (Path p : direct) {
            if (Files.isRegularFile(p)) {
                return p;
            }
        }

        Path walk = base;
        for (int i = 0; i < 6 && walk != null; i++) {
            Path inBack = walk.resolve("back").resolve(".env");
            if (Files.isRegularFile(inBack)) {
                return inBack;
            }
            Path rootEnv = walk.resolve(".env");
            if (Files.isRegularFile(rootEnv)) {
                return rootEnv;
            }
            walk = walk.getParent();
        }
        return null;
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
