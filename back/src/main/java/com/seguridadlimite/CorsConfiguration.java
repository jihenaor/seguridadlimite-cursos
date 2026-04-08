package com.seguridadlimite;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuración de CORS.
 *
 * Política:
 *  - Solo se permiten orígenes HTTPS en producción.
 *  - localhost:4200 solo para desarrollo local.
 *  - En producción el gateway Nginx también filtra CORS;
 *    esta capa es una segunda línea de defensa.
 */
@Configuration
@EnableWebMvc
public class CorsConfiguration implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedOrigins(
                        // ── Producción — solo HTTPS ───────────────────────────
                        "https://cursos.seguridadallimite.com",
                        "https://admin.seguridadallimite.com",
                        "https://trabajador.seguridadallimite.com",
                        // ── Desarrollo local ──────────────────────────────────
                        "http://localhost:4200",
                        "http://localhost:4201"   // panel-admin en dev
                )
                .allowedHeaders("Authorization", "Content-Type", "Accept")
                .maxAge(3600);
    }
}
