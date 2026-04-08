import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { environment } from './../../../environments/environment';
import { Observable, map } from 'rxjs';

import { Aprendiz } from '@model/aprendiz.model';

@Injectable({
  providedIn: 'root'
})

export class UpdateAprendizService {
  constructor(private http: HttpClient) {}

  updatePerfil(datos: Aprendiz): Observable<any> {

    const url = `${environment.apiUrl}/aprendiz/updatePerfil`;
    return this.http.post<any>(url, datos).pipe(
        map(response => {

        return response;
      })
    );
  }
}
