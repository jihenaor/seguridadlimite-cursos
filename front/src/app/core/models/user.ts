import { Role } from './role';

/**
 * Modelo del usuario autenticado.
 *
 * NOTA DE SEGURIDAD: No incluye el campo 'password'.
 * El back-end nunca devuelve contraseñas en sus respuestas.
 * El token JWT es el único mecanismo de autenticación persistente.
 *
 * El campo 'role' refleja directamente sl_personal.role en la BD
 * (ej. ADMINISTRADOR, COORDINADOR, INSTRUCTOR). Para Empresa: 'C'. Para Trabajador: 'T'.
 */
export class User {
  id: number;
  img: string;
  username: string;
  nombreusuario: string;
  numerodocumento: string;
  lastName: string;
  role: Role;
  token?: string;
}
