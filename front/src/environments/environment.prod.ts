/**
 * Producción detrás del nginx de infraestructura (gateway.conf):
 *   location /api/ { proxy_pass http://backend:8090/cursosback/api/; }
 * El cliente debe llamar https://cursos.seguridadallimite.com/api/... (sin /cursosback).
 * Si usas .../cursosback/api/... la petición cae en el SPA y devuelve 405 en POST.
 */
export const environment = {
  production: true,
  apiUrl: 'https://cursos.seguridadallimite.com/api',
};
