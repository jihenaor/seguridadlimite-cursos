-- =============================================================================
-- Migración idempotente: columnas de permiso_trabajo_alturas requeridas por JPA
-- (PermisoTrabajoAlturas.java / insert Hibernate).
--
-- Corrige entre otros: Unknown column 'idpersonaautoriza1' in 'field list'
--
-- Si ya aplicaste 001_align_jpa_wwsegu_cursos.sql por completo, muchas de estas
-- columnas ya existirán; el procedimiento no hace nada en ese caso.
--
-- BACKUP recomendado. Base: wwsegu_cursos · MySQL 8.x
--
-- El UPDATE de migración usa la PK id_permiso en el WHERE para cumplir el
-- "safe update mode" de MySQL Workbench (error 1175).
-- =============================================================================

USE `wwsegu_cursos`;

DROP PROCEDURE IF EXISTS `sp_align_permiso_trabajo_alturas_columns`;

DELIMITER //

CREATE PROCEDURE `sp_align_permiso_trabajo_alturas_columns`()
BEGIN
  -- numerogrupos (JPA: numerogrupos)
  IF NOT EXISTS (
    SELECT 1 FROM information_schema.COLUMNS
    WHERE TABLE_SCHEMA = DATABASE()
      AND TABLE_NAME = 'permiso_trabajo_alturas'
      AND COLUMN_NAME = 'numerogrupos'
  ) THEN
    ALTER TABLE `permiso_trabajo_alturas`
      ADD COLUMN `numerogrupos` int NOT NULL DEFAULT 1;
  END IF;

  -- idpersonaautoriza1
  IF NOT EXISTS (
    SELECT 1 FROM information_schema.COLUMNS
    WHERE TABLE_SCHEMA = DATABASE()
      AND TABLE_NAME = 'permiso_trabajo_alturas'
      AND COLUMN_NAME = 'idpersonaautoriza1'
  ) THEN
    ALTER TABLE `permiso_trabajo_alturas`
      ADD COLUMN `idpersonaautoriza1` int NULL;
  END IF;

  -- idpersonaautoriza2
  IF NOT EXISTS (
    SELECT 1 FROM information_schema.COLUMNS
    WHERE TABLE_SCHEMA = DATABASE()
      AND TABLE_NAME = 'permiso_trabajo_alturas'
      AND COLUMN_NAME = 'idpersonaautoriza2'
  ) THEN
    ALTER TABLE `permiso_trabajo_alturas`
      ADD COLUMN `idpersonaautoriza2` int NULL;
  END IF;

  -- idresponsableemeergencias (nombre alineado con el campo Java)
  IF NOT EXISTS (
    SELECT 1 FROM information_schema.COLUMNS
    WHERE TABLE_SCHEMA = DATABASE()
      AND TABLE_NAME = 'permiso_trabajo_alturas'
      AND COLUMN_NAME = 'idresponsableemeergencias'
  ) THEN
    ALTER TABLE `permiso_trabajo_alturas`
      ADD COLUMN `idresponsableemeergencias` int NULL;
  END IF;

  -- dias (JPA PermisoTrabajoAlturas.dias)
  IF NOT EXISTS (
    SELECT 1 FROM information_schema.COLUMNS
    WHERE TABLE_SCHEMA = DATABASE()
      AND TABLE_NAME = 'permiso_trabajo_alturas'
      AND COLUMN_NAME = 'dias'
  ) THEN
    ALTER TABLE `permiso_trabajo_alturas`
      ADD COLUMN `dias` int NULL;
  END IF;

  -- Copia desde columna legada idpersonaautoriza si aún existe
  IF EXISTS (
    SELECT 1 FROM information_schema.COLUMNS
    WHERE TABLE_SCHEMA = DATABASE()
      AND TABLE_NAME = 'permiso_trabajo_alturas'
      AND COLUMN_NAME = 'idpersonaautoriza'
  ) THEN
    UPDATE `permiso_trabajo_alturas`
    SET `idpersonaautoriza1` = `idpersonaautoriza`
    WHERE `id_permiso` IS NOT NULL
      AND `idpersonaautoriza` IS NOT NULL
      AND `idpersonaautoriza1` IS NULL;
  END IF;
END//

DELIMITER ;

CALL `sp_align_permiso_trabajo_alturas_columns`();

DROP PROCEDURE IF EXISTS `sp_align_permiso_trabajo_alturas_columns`;

-- -----------------------------------------------------------------------------
-- Índice único (id_nivel, fecha_inicio, idpersonaautoriza1)
-- Si sigues con el UNIQUE antiguo sobre idpersonaautoriza, ejecuta el bloque
-- completo de 001_align_jpa_wwsegu_cursos.sql (índice en id_nivel, DROP antiguo,
-- ADD COLUMN si faltan, DROP idpersonaautoriza, ADD nuevo UNIQUE).
-- Este script 004 no elimina idpersonaautoriza para no romper FKs/índices aún
-- referenciados.
-- -----------------------------------------------------------------------------
