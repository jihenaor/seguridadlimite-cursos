package com.seguridadlimite.security.util;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * Roles del sistema alineados con la entidad de negocio.
 *
 * Correspondencia con el front-end (campo 'perfil' / Role enum):
 *   ADMIN       ↔  'A'  (Admin en front)
 *   INSTRUCTOR  ↔  'E'  (Teacher en front)
 *   EMPRESA     ↔  'C'  (Company en front)
 *   TRABAJADOR  ↔  'T'  (Student en front)
 *
 * NOTA DE MIGRACIÓN: Si la tabla 'user' ya tiene filas con roles
 * anteriores ('ADMINISTRATOR', 'CUSTOMER'), ejecutar:
 *   UPDATE user SET role = 'ADMIN'      WHERE role = 'ADMINISTRATOR';
 *   UPDATE user SET role = 'INSTRUCTOR' WHERE role = 'CUSTOMER';
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
}
