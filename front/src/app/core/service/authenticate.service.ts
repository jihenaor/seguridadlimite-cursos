/**
 * AuthenticateService — DEPRECADO
 *
 * Este servicio duplicaba la lógica de AuthService y tenía un bug
 * (el Observable nunca se suscribía, por lo que la petición HTTP
 * nunca se ejecutaba).
 *
 * Se mantiene como wrapper que delega en AuthService para no romper
 * componentes que aún lo inyecten.
 *
 * MIGRACIÓN: reemplaza cualquier inyección de AuthenticateService
 * por AuthService directamente.
 *
 * @deprecated Usa AuthService en su lugar.
 */
import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthenticateService {

  constructor(private authService: AuthService) {}

  /**
   * @deprecated Usa AuthService.login() directamente.
   */
  login(username: string, password: string): Observable<any> {
    return this.authService.login(username, password);
  }

  get usuario(): any {
    return this.authService.currentUserValue;
  }
}
