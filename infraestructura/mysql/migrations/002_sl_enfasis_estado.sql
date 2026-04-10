-- =============================================================================
-- sl_enfasis.estado — usado por Enfasis.java y EnfasisRestController (filtro "A")
-- La tabla legacy suele tener solo id + nombre.
-- Omitir si la columna ya existe.
-- =============================================================================

USE `wwsegu_cursos`;

ALTER TABLE `sl_enfasis`
  ADD COLUMN `estado` varchar(1) NOT NULL DEFAULT 'A' AFTER `nombre`;
