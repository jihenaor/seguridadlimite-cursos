package com.seguridadlimite.security.util;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Roles del sistema alineados con la entidad de negocio.
 *
 * Correspondencia con el front-end (campo 'perfil' / Role enum):
 *   ADMIN       ↔  'A'  (Admin en front)
 *   INSTRUCTOR  ↔  'E'  (Teacher en front)
 *   EMPRESA     ↔  'C'  (Company en front)
 *   TRABAJADOR  ↔  'T'  (Student en front)
 *
 * Código en {@code sl_personal.role}: A, E, C, T. También se aceptan cadenas tipo {@code ROLE_ADMIN}.
 *
 * El back-end es la fuente de verdad para autorización; el front-end
 * solo usa los roles para control de navegación (UX), no de seguridad.
 */
@Getter
public enum Role {

    ADMIN(Arrays.asList(
            Permission.READ_CURSOS,
            Permission.WRITE_CURSOS,
            Permission.MANAGE_USUARIOS,
            Permission.MANAGE_EMPRESAS,
            Permission.VIEW_REPORTES,
            Permission.GESTIONAR_INSCRIPCIONES,
            Permission.REALIZAR_EVALUACIONES
    )),

    INSTRUCTOR(Arrays.asList(
            Permission.READ_CURSOS,
            Permission.WRITE_CURSOS,
            Permission.VIEW_REPORTES,
            Permission.GESTIONAR_INSCRIPCIONES,
            Permission.REALIZAR_EVALUACIONES
    )),

    EMPRESA(Arrays.asList(
            Permission.READ_CURSOS,
            Permission.VIEW_REPORTES,
            Permission.GESTIONAR_INSCRIPCIONES
    )),

    TRABAJADOR(Arrays.asList(
            Permission.READ_CURSOS,
            Permission.REALIZAR_EVALUACIONES
    ));

    private final List<Permission> permissions;

    Role(List<Permission> permissions) {
        this.permissions = permissions;
    }

    /**
     * Interpreta un valor de {@code authorities.authority} (p. ej. ROLE_ADMIN, ROLE_USER).
     * El orden del enum define prioridad: ordinal menor = más privilegio ({@link #ADMIN} primero).
     */
    public static Role fromSpringSecurityAuthority(String authority) {
        if (authority == null || authority.isBlank()) {
            return TRABAJADOR;
        }
        String u = authority.toUpperCase();
        if (u.contains("ADMIN")) {
            return ADMIN;
        }
        if (u.contains("INSTRUCTOR") || u.contains("TEACHER") || u.contains("ENTRENADOR")) {
            return INSTRUCTOR;
        }
        if (u.contains("EMPRESA") || u.contains("COMPANY")) {
            return EMPRESA;
        }
        return TRABAJADOR;
    }

    /** Valor de {@link com.seguridadlimite.models.personal.dominio.Personal#getRole()} (A, E, C, T u otros). */
    public static Role fromPersonalRoleCode(String code) {
        if (code == null || code.isBlank()) {
            return TRABAJADOR;
        }
        return switch (code.trim().toUpperCase()) {
            case "A" -> ADMIN;
            case "E" -> INSTRUCTOR;
            case "C" -> EMPRESA;
            case "T" -> TRABAJADOR;
            default -> fromSpringSecurityAuthority(code);
        };
    }

    public static List<GrantedAuthority> asSpringAuthorities(Role role) {
        List<GrantedAuthority> authorities = role.getPermissions().stream()
                .map(p -> new SimpleGrantedAuthority(p.name()))
                .collect(Collectors.toCollection(ArrayList::new));
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role.name()));
        return authorities;
    }
}
