package com.seguridadlimite.config;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Filtro de Rate Limiting por IP usando Bucket4j.
 *
 * Límites configurados:
 *  - /auth/authenticate : 10 solicitudes / minuto  (protección contra fuerza bruta)
 *  - Resto de endpoints : 300 solicitudes / minuto (uso normal de la API)
 *
 * Registrado en FilterConfig con orden 1 (primer filtro de la cadena).
 */
@org.springframework.stereotype.Component
public class RateLimitingFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(RateLimitingFilter.class);

    private static final int LIMITE_AUTH = 10;         // solicitudes por minuto al endpoint de login
    private static final int LIMITE_API  = 300;        // solicitudes por minuto al resto de la API

    /** Buckets separados para el endpoint de autenticación (anti fuerza bruta) */
    private final Map<String, Bucket> cacheAuth = new ConcurrentHashMap<>();

    /** Buckets para el resto de endpoints de la API */
    private final Map<String, Bucket> cacheApi  = new ConcurrentHashMap<>();

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String ip  = obtenerIpReal(request);
        String uri = request.getRequestURI();

        boolean esLogin = uri.contains("/auth/authenticate")
                || uri.contains("/api/authenticate");
        Bucket bucket = esLogin
                ? cacheAuth.computeIfAbsent(ip, k -> crearBucket(LIMITE_AUTH))
                : cacheApi .computeIfAbsent(ip, k -> crearBucket(LIMITE_API));

        if (bucket.tryConsume(1)) {
            filterChain.doFilter(request, response);
        } else {
            logger.warn("Rate limit superado — IP: {} URI: {}", ip, uri);
            response.setStatus(429);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\":\"Demasiadas solicitudes. Intenta de nuevo en un minuto.\"}");
        }
    }

    // ─── helpers ────────────────────────────────────────────────────────────

    private Bucket crearBucket(int limite) {
        return Bucket.builder()
                .addLimit(Bandwidth.classic(limite, Refill.intervally(limite, Duration.ofMinutes(1))))
                .build();
    }

    /**
     * Respeta el header X-Forwarded-For para obtener la IP real
     * cuando la app está detrás de un proxy/Nginx.
     */
    private String obtenerIpReal(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isBlank()) {
            return xForwardedFor.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }
}
