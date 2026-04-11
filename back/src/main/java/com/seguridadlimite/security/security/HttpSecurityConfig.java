package com.seguridadlimite.security.security;

import lombok.RequiredArgsConstructor;

import com.seguridadlimite.security.security.filter.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * Configuración de seguridad HTTP.
 *
 * Política de autorización:
 *  - /auth/**          → público (login JWT vía AuthenticationController)
 *  - POST /api/authenticate* → público (login legacy en AuthController, usado por los front con proxy /api)
 *  - /api/aprendiz/**  → en su mayoría público (lectura + save); ver reglas abajo
 *  - /api/evaluacion/** → público GET/POST (cuestionarios de ingreso/teórica/encuesta sin JWT)
 *  - POST /api/registrarevaluacion* → público (envío de respuestas del mismo flujo)
 *  - POST /api/updateFoto → público (foto en flujo de inscripción sin JWT, análogo a save)
 *  - /api/admin/**     → requiere rol ROLE_ADMIN
 *  - Resto de /api/**  → requiere autenticación JWT válida
 *
 * CSRF deshabilitado intencionalmente: la API es stateless (JWT),
 * no usa cookies de sesión, por lo que CSRF no aplica.
 */
@Component
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class HttpSecurityConfig {

    private final JwtAuthenticationFilter authenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .authorizeHttpRequests(builderRequestMatchers());

        return http.build();
    }

    private static Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>
            .AuthorizationManagerRequestMatcherRegistry> builderRequestMatchers() {

        return authConfig -> {

            // ── Endpoints públicos ────────────────────────────────────────────
            authConfig.requestMatchers(HttpMethod.POST, "/auth/authenticate").permitAll();
            authConfig.requestMatchers(
                    HttpMethod.POST,
                    "/api/authenticate",
                    "/api/authenticateempresa",
                    "/api/authenticatetrabajador").permitAll();
            authConfig.requestMatchers(HttpMethod.GET,  "/auth/public-access").permitAll();
            authConfig.requestMatchers("/error").permitAll();
            authConfig.requestMatchers(
                    "/v3/api-docs/**",
                    "/swagger-ui/**",
                    "/swagger-ui.html").permitAll();

            // ── Endpoints de quiz/evaluación para trabajadores (sin login) ───
            // Los trabajadores acceden directamente con su número de documento
            authConfig.requestMatchers(HttpMethod.GET,  "/api/aprendiz/evaluacion/**").permitAll();
            authConfig.requestMatchers(HttpMethod.POST, "/api/aprendiz/evaluacion/**").permitAll();
            // Preguntas y envío bajo /api/evaluacion (p. ej. GET .../ingreso, .../teorica)
            authConfig.requestMatchers(HttpMethod.GET,  "/api/evaluacion/**").permitAll();
            authConfig.requestMatchers(HttpMethod.POST, "/api/evaluacion/**").permitAll();
            // Registro de respuestas (controladores con @RequestMapping("/api"))
            authConfig.requestMatchers(HttpMethod.POST, "/api/registrarevaluacioningreso/**").permitAll();
            authConfig.requestMatchers(HttpMethod.POST, "/api/registrarevaluacionteorica/**").permitAll();

            // ── Datos de referencia usados en el formulario público de inscripción ──
            // Estos endpoints son de solo lectura y los necesita el flujo de
            // auto-inscripción del trabajador, que no requiere login previo.
            authConfig.requestMatchers(HttpMethod.GET,
                    "/api/empresas",
                    "/api/epss",
                    "/api/arls",
                    "/api/enfasis-inscripcion",
                    "/api/documentos",
                    "/api/gruposactivosinscripcion",
                    "/api/nivel/activosfecha",
                    "/api/nivel/activosinscripcion",
                    "/api/permisos/inscripciones-abiertas").permitAll();
            authConfig.requestMatchers(HttpMethod.GET,
                    "/api/trabajadorinscripcion/**",
                    "/api/aprendiz/**").permitAll();
            // Guardar inscripción desde el formulario público
            authConfig.requestMatchers(HttpMethod.POST, "/api/aprendiz/save").permitAll();
            // Foto del trabajador desde /student/photo (flujo inscripción sin JWT; mismo riesgo modelo que save)
            authConfig.requestMatchers(HttpMethod.POST, "/api/updateFoto").permitAll();

            // ── Endpoints protegidos — cualquier usuario autenticado ──────────
            authConfig.requestMatchers("/api/**").authenticated();

            // ── Cualquier otra ruta no listada: denegar ───────────────────────
            authConfig.anyRequest().denyAll();
        };
    }
}
