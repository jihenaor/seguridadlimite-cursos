import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Observable, catchError, map, throwError } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Pregunta } from '../../core/models/pregunta.model';
import { RespuestaEncuestaSatisfaccion } from '../../core/models/respuestaencuestasatisfaccion.model';

@Injectable({
  providedIn: 'root'
})

export class SaveencuestasatisfaccionclienteService {
  constructor(private http: HttpClient) {}

    saveEvaluacion(RespuestaEncuestaSatisfaccion: RespuestaEncuestaSatisfaccion, idaprendiz: number): Observable<any> {
    const url = `${environment.apiUrl}/evaluacion/${idaprendiz}/encuestasatisfaccioncliente`;
    return this.http.post<any>(url, RespuestaEncuestaSatisfaccion).pipe(
      map(response => {

        return response;
      }),
      catchError(error => {
        // Realizar cualquier manipulación de errores aquí si es necesario
        return throwError(() => error.error.message);
      })
    );
  }
}
