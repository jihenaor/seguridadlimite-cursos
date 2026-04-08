package com.seguridadlimite.security.security.filter;

import com.seguridadlimite.security.entity.User;
import com.seguridadlimite.security.repository.UserRepository;
import com.seguridadlimite.security.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // 1. Leer el header Authorization
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 2. Extraer el JWT
        String jwt = authHeader.split(" ")[1].trim();

        // 3. Validar firma y expiración antes de continuar
        if (!jwtService.isTokenValid(jwt)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"error\":\"Token inválido o expirado\"}");
            return;
        }

        // 4. Extraer username
        String username;
        try {
            username = jwtService.extractUsername(jwt);
        } catch (Exception e) {
            logger.warn("No se pudo extraer username del JWT: {}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"error\":\"Token malformado\"}");
            return;
        }

        // 5. Buscar usuario en BD para obtener sus roles (opcional).
        // Si el username pertenece al sistema legacy (Personal/Empresa/Trabajador)
        // puede no existir en la tabla security 'user' — igual se autentica con JWT válido.
        Optional<User> userOptional = userRepository.findByUsername(username);
        Collection<? extends GrantedAuthority> authorities = userOptional
                .map(User::getAuthorities)
                .orElseGet(() -> {
                    logger.debug("Username '{}' no está en tabla security/user; se autentica con JWT sin roles específicos.", username);
                    return Collections.emptyList();
                });

        // 6. Registrar autenticación en el SecurityContext
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                username, null, authorities
        );
        SecurityContextHolder.getContext().setAuthentication(authToken);

        // 7. Continuar con la cadena de filtros
        filterChain.doFilter(request, response);
    }
}
