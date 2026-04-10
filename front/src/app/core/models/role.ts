/**
 * Roles del sistema — coinciden exactamente con sl_personal.role en la BD.
 * Para Empresa (C) y Trabajador (T) se usan códigos cortos propios del login.
 */
export enum Role {
  Administrador  = 'ADMINISTRADOR',
  Coordinador    = 'COORDINADOR',
  Instructor     = 'INSTRUCTOR',
  Company        = 'C',
  Student        = 'T',
}
