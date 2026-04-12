package com.seguridadlimite.security.security.filter;

import lombok.RequiredArgsConstructor;

import com.seguridadlimite.models.personal.application.PersonalService;
import com.seguridadlimite.models.personal.dominio.Personal;
import com.seguridadlimite.security.service.JwtService;
import com.seguridadlimite.security.util.Role;
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

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final JwtService jwtService;

    private final PersonalService personalService;

    /**
     * Login sin sesión: no validar JWT aquí (evita 401 si el cliente envía un Bearer caducado).
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String uri = request.getRequestURI();
        String cp = request.getContextPath();
        if (cp != null && !cp.isEmpty() && uri.startsWith(cp)) {
            uri = uri.substring(cp.length());
        }
        return "/api/authenticate".equals(uri)
                || "/api/authenticateempresa".equals(uri)
                || "/api/authenticatetrabajador".equals(uri);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = authHeader.split(" ")[1].trim();

        if (!jwtService.isTokenValid(jwt)) {
            logger.warn("JWT rechazado (inválido o expirado) — URI: {}", request.getRequestURI());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"error\":\"Token inválido o expirado\"}");
            return;
        }

        String username;
        try {
            username = jwtService.extractUsername(jwt);
        } catch (Exception e) {
            logger.warn("No se pudo extraer username del JWT: {}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"error\":\"Token malformado\"}");
            return;
        }

        // Subject del JWT = mismo identificador que en login (documento o email en sl_personal).
        Personal personal = personalService.findByLogin(username);
        Collection<? extends GrantedAuthority> authorities;
        if (personal != null) {
            Role role = Role.fromPersonalRoleCode(personal.getRole());
            authorities = Role.asSpringAuthorities(role);
        } else {
            logger.debug("Subject JWT '{}' no encontrado en sl_personal; se autentica sin authorities de BD.",
                    username);
            authorities = Collections.emptyList();
        }

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                username, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}
