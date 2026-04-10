-- =============================================================================
-- permiso_fechas: índice único (id_permiso, dia, contexto, unidad)
--
-- Sustituye idx_fecha_permiso (id_permiso, fecha) para permitir la misma fecha
-- varias veces si cambian dia / contexto / unidad.
--
-- Antes de ejecutar: comprobar que no hay duplicados con:
--   SELECT id_permiso, dia, contexto, unidad, COUNT(*) c
--   FROM permiso_fechas
--   GROUP BY id_permiso, dia, contexto, unidad
--   HAVING c > 1;
--
-- BACKUP. Base: wwsegu_cursos · MySQL 8.x
-- =============================================================================

USE `wwsegu_cursos`;

ALTER TABLE `permiso_fechas`
  DROP INDEX `idx_fecha_permiso`;

ALTER TABLE `permiso_fechas`
  ADD UNIQUE KEY `uk_permiso_fechas_permiso_dia_contexto_unidad` (
    `id_permiso`,
    `dia`,
    `contexto`,
    `unidad`
  );

-- Tras aplicar este script: en JPA actualizar PermisoFechas @UniqueConstraint a
--   name = uk_permiso_fechas_permiso_dia_contexto_unidad
--   columnNames = id_permiso, dia, contexto, unidad
-- y, si necesitas varias filas con la misma fecha, cambiar la deduplicación en
-- RegistrarPermisosTrabajoService.registrarPermisoFechas (hoy: una fila por fecha).
