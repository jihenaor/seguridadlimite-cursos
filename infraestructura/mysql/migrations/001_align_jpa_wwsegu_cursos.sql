-- =============================================================================
-- Migración: alinear esquema MySQL con entidades JPA (Hibernate validate / prod)
--
-- Bases antiguas: columna única idpersonaautoriza, sin numerogrupos / idpersonaautoriza2,
-- tablas grupo_chequeo en minúsculas vs @Table GrupoChequeo en Java.
--
-- Hacer BACKUP antes. Ejecutar por bloques y omitir lo que ya esté aplicado.
-- MySQL 8.x · base: wwsegu_cursos
-- =============================================================================

USE `wwsegu_cursos`;

-- -----------------------------------------------------------------------------
-- 1) Tablas de chequeo (solo si siguen como grupo_chequeo / detalle_chequeo)
--    En Linux con tablas case-sensitive, Hibernate busca GrupoChequeo / DetalleChequeo.
-- -----------------------------------------------------------------------------
-- RENAME TABLE `grupo_chequeo` TO `GrupoChequeo`;
-- RENAME TABLE `detalle_chequeo` TO `DetalleChequeo`;

-- -----------------------------------------------------------------------------
-- 2) permiso_trabajo_alturas ↔ PermisoTrabajoAlturas.java
-- -----------------------------------------------------------------------------

-- La FK id_nivel -> sl_niveles suele usar el UNIQUE (id_nivel, fecha_inicio, idpersonaautoriza)
-- como índice de soporte (prefijo id_nivel). Sin otro índice en id_nivel, MySQL devuelve:
--   #1553 - Cannot drop index ... needed in a foreign key constraint
-- Crear primero un índice solo en id_nivel; si ya existe uno equivalente, omite esta línea.
ALTER TABLE `permiso_trabajo_alturas`
  ADD KEY `idx_permiso_trabajo_alturas_id_nivel` (`id_nivel`);

-- Índice único antiguo. Quitar antes de eliminar la columna idpersonaautoriza.
ALTER TABLE `permiso_trabajo_alturas` DROP INDEX `idx_nivel_fecha_autoriza_unique`;

-- Una sentencia por columna: comenta la que ya exista en tu servidor.
ALTER TABLE `permiso_trabajo_alturas`
  ADD COLUMN `numerogrupos` int NOT NULL DEFAULT 1 AFTER `codigoministerio`;

ALTER TABLE `permiso_trabajo_alturas`
  ADD COLUMN `idpersonaautoriza1` int DEFAULT NULL AFTER `condicion_salud_trabajador`;

ALTER TABLE `permiso_trabajo_alturas`
  ADD COLUMN `idpersonaautoriza2` int DEFAULT NULL AFTER `idpersonaautoriza1`;

-- Copiar datos del campo antiguo al nuevo (solo si existía idpersonaautoriza)
UPDATE `permiso_trabajo_alturas`
SET `idpersonaautoriza1` = `idpersonaautoriza`
WHERE `idpersonaautoriza` IS NOT NULL;

ALTER TABLE `permiso_trabajo_alturas` DROP COLUMN `idpersonaautoriza`;

-- Solo si NO tienes columna dias todavía:
-- ALTER TABLE `permiso_trabajo_alturas`
--   ADD COLUMN `dias` int NOT NULL DEFAULT 1 AFTER `cupofinal`;

ALTER TABLE `permiso_trabajo_alturas`
  ADD UNIQUE KEY `idx_nivel_fecha_autoriza_unique` (`id_nivel`,`fecha_inicio`,`idpersonaautoriza1`);

-- =============================================================================
-- Tras aplicar: desplegar backend con ddl-auto=validate y revisar logs.
-- =============================================================================
