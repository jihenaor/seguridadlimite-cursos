import { Role } from './role';

/**
 * Modelo del usuario autenticado.
 *
 * NOTA DE SEGURIDAD: No incluye el campo 'password'.
 * El back-end nunca devuelve contraseñas en sus respuestas.
 * El token JWT es el único mecanismo de autenticación persistente.
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
