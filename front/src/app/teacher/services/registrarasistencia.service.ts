import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Observable, map } from 'rxjs';
import { environment } from './../../../environments/environment';

import { Asistencia } from '../../core/models/asistencia.model';

@Injectable({
  providedIn: 'root',
})
export class RegistrarasistenciaService {
  constructor(private http: HttpClient) {}

    registrar(asistencia: Asistencia): Observable<any> {
    const url = `${environment.apiUrl}/asistencia`;
    return this.http.post<any>(url, asistencia).pipe(
      map(response => {
        return response;
      })
    );
  }
}
