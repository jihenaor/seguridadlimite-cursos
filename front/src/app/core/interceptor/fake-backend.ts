/**
 * FAKE BACKEND — DESHABILITADO
 *
 * Este archivo existía para simular el back-end durante el desarrollo
 * inicial. Ha sido reemplazado por el back-end real (Spring Boot).
 *
 * Se mantiene el archivo vacío para no romper imports existentes,
 * pero el interceptor ya NO intercepta ninguna petición HTTP.
 *
 * TODO: eliminar este archivo y sus imports en el próximo refactor de módulos.
 */
import { HTTP_INTERCEPTORS } from '@angular/common/http';

// Exportación vacía — no registra ningún interceptor
export const fakeBackendProvider: any[] = [];
