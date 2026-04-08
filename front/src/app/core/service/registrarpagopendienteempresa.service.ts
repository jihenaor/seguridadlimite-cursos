import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Observable, map } from 'rxjs';

import { environment } from 'src/environments/environment';
import { Pagopendienteempresa } from '../models/pagopendienteempresa.model';
import { Aprendiz } from '../models/aprendiz.model';

@Injectable({
  providedIn: 'root'
})
export class RegistrarpagopendienteempresaService {
  constructor(private http: HttpClient) {}

    register(pagopendienteempresa: Pagopendienteempresa): Observable<any> {
    const url = `${environment.apiUrl}/aprendiz/registrarpagopendienteempresa/`;
    return this.http.post<any>(url, pagopendienteempresa).pipe(
      map(response => {

        return response;
      }),
    );
  }

  registerAprendiz(aprendiz: Aprendiz): Observable<any> {
    const url = `${environment.apiUrl}/aprendiz/registrarpagopendienteaprendiz/`;
    return this.http.post<any>(url, aprendiz).pipe(
      map(response => {

        return response;
      }),
    );
  }
}
