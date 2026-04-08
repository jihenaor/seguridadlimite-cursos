/**
 * AuthService — Servicio central de autenticación.
 *
 * NOTA DE SEGURIDAD (HAL-F01):
 * El token JWT se almacena en sessionStorage, que es accesible desde
 * JavaScript. Si la aplicación es vulnerable a XSS, un atacante podría
 * robar el token. Para mayor seguridad, considerar migrar a cookies
 * HttpOnly + SameSite=Strict (requiere cambios en el back-end).
 * Mitigación actual: la CSP del gateway Nginx limita la carga de scripts
 * externos. Asegurarse de que la política CSP no incluya 'unsafe-eval'.
 *
 * NOTA: La autorización real ocurre en el back-end.
 * El AuthGuard del front solo controla la navegación (UX), no la seguridad.
 */
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { User } from '../models/user';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private currentUserSubject: BehaviorSubject<User>;
  public currentUser: Observable<User>;

  private loggedIn = new BehaviorSubject<boolean>(false);


  constructor(private http: HttpClient) {
    this.currentUserSubject = new BehaviorSubject<User>(
      JSON.parse(sessionStorage.getItem('currentUser'))
    );
    this.currentUser = this.currentUserSubject.asObservable();
  }

  public get currentUserValue(): User {
    return this.currentUserSubject.value;
  }

  login(username: string, password: string) {
    return this.http
      .post<any>(`${environment.apiUrl}/authenticate`, {
        username,
        password,
      })
      .pipe(
        map((user) => {
          sessionStorage.setItem('currentUser', JSON.stringify(user));
          this.currentUserSubject.next(user);
          return user;
        })
      );
  }

  logout() {
    // remove user from local storage to log user out
    sessionStorage.removeItem('currentUser');
    this.currentUserSubject.next(null);
    return of({ success: false });
  }


  getItem(item: string) {
    return sessionStorage.getItem(item);
  }

  saveToken(res: any) {
    this.loggedIn.next(true);
    if (res !== undefined && res.token !== undefined) {
      sessionStorage.setItem('token', res.token);
      sessionStorage.setItem('perfil', res.perfil);
      if (res.role === 'C') { // Company
        sessionStorage.setItem('idempresa', res.id);
      } else {
        sessionStorage.setItem('id', res.id);
        sessionStorage.removeItem('idempresa');
      }
      sessionStorage.setItem('nombreusuario', res.nombreusuario);
    }
  }

  saveForm(item: string, datos: string) {
    sessionStorage.setItem(item, JSON.stringify(datos));
  }

  async post(nombreservicio: string, entidad: any, save: boolean) {
    const url = `${environment.apiUrl}${nombreservicio}`;
    const datos = JSON.stringify(entidad);

    const response = await fetch(url,
      {
        method: 'POST',
        body: datos,
        headers: {
          'Content-Type': 'application/json',
        }
      });
    if (response.ok) {
      const data = await response.json();

      if (save) {
        sessionStorage.setItem('currentUser', JSON.stringify(data));

        this.currentUserSubject.next(data);
      }
      return data;
    } else {
      switch (response.status) {
        case 401:
          throw new Error('No tiene autorizacion para ingresar al sistema');
        default:
          throw new Error('Se genero un error ' + response.statusText);
      }
    }
  }
}
