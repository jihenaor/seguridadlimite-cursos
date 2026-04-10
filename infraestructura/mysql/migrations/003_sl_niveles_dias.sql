-- =============================================================================
-- sl_niveles.dias — mapeado por Nivel.java (sin @Transient)
-- Esquema antiguo: tabla sin esta columna → Unknown column 'n1_0.dias'
-- Omitir si ya existe.
-- =============================================================================

USE `wwsegu_cursos`;

-- Valor por defecto 1 (coherente con RegistrarPermisosTrabajoService / permisos)
ALTER TABLE `sl_niveles`
  ADD COLUMN `dias` int DEFAULT 1 AFTER `duraciontotal`;

UPDATE `sl_niveles` SET `dias` = 1 WHERE `dias` IS NULL;
