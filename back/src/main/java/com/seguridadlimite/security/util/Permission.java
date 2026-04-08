package com.seguridadlimite.security.util;

/**
 * Permisos granulares del sistema.
 *
 * Matriz de acceso:
 * ┌────────────────────────────────┬───────┬────────────┬────────┬────────────┐
 * │ Permiso                        │ ADMIN │ INSTRUCTOR │EMPRESA │TRABAJADOR  │
 * ├────────────────────────────────┼───────┼────────────┼────────┼────────────┤
 * │ READ_CURSOS                    │  ✓    │     ✓      │   ✓    │     ✓      │
 * │ WRITE_CURSOS                   │  ✓    │     ✓      │        │            │
 * │ MANAGE_USUARIOS                │  ✓    │            │        │            │
 * │ MANAGE_EMPRESAS                │  ✓    │            │        │            │
 * │ VIEW_REPORTES                  │  ✓    │     ✓      │   ✓    │            │
 * │ GESTIONAR_INSCRIPCIONES        │  ✓    │     ✓      │   ✓    │            │
 * │ REALIZAR_EVALUACIONES          │  ✓    │     ✓      │        │     ✓      │
 * └────────────────────────────────┴───────┴────────────┴────────┴────────────┘
 */
public enum Permission {

    // ── Cursos ──────────────────────────────────────────────────────────────
    READ_CURSOS,
    WRITE_CURSOS,

    // ── Administración ──────────────────────────────────────────────────────
    MANAGE_USUARIOS,
    MANAGE_EMPRESAS,

    // ── Reportes e inscripciones ─────────────────────────────────────────────
    VIEW_REPORTES,
    GESTIONAR_INSCRIPCIONES,

    // ── Evaluaciones ─────────────────────────────────────────────────────────
    REALIZAR_EVALUACIONES;
}
