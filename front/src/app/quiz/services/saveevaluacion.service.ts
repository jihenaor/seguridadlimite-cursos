import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { environment } from './../../../environments/environment';
import { Observable, map } from 'rxjs';

import { RespuestaEvaluacion } from '../interfaces/quiz.interfaces';


@Injectable()
export class SaveevaluacionService {
  constructor(private http: HttpClient) {}

    saveEvaluacion(preguntas: RespuestaEvaluacion[], idaprendiz: number, endpoint: string): Observable<any> {
    const url = `${environment.apiUrl}/${endpoint}/${idaprendiz}`;
    return this.http.post<any>(url, preguntas).pipe(
      map(response => {

        return response;
      }),
    );
  }
}
