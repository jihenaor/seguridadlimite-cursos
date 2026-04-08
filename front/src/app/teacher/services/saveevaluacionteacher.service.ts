import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Observable, map } from 'rxjs';
import { environment } from './../../../environments/environment';

import { Grupoevaluacion } from '../../core/models/grupoevaluacion.model';

@Injectable({
  providedIn: 'root',
})
export class SaveevaluacionteacherService {
  constructor(private http: HttpClient) {}

    saveEvaluacion(preguntas: Grupoevaluacion[]): Observable<any> {
    const url = `${environment.apiUrl}/evaluacion/practica`;
    return this.http.post<any>(url, preguntas).pipe(
      map(response => {
        return response;
      })
    );
  }
}
