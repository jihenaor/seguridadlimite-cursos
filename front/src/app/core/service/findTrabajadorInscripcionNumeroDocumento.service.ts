
import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { Trabajador } from '@model/trabajador.model';

import { environment } from 'src/environments/environment';


@Injectable({
  providedIn: 'root',
})
export class FindTrabajadorInscripcionNumeroDocumentoService {

  private apiUrl = `${environment.apiUrl}`;

  constructor(private http: HttpClient) {}

  getTrabajadorInscripcion(numerodocumento: string): Observable<Trabajador> {
    return this.http
      .get<Trabajador>(`${this.apiUrl}/trabajadorinscripcion/${numerodocumento}`, { observe: 'response' })
      .pipe(
        map((response) => {
          if (response.status === 200) {
            return response.body as Trabajador; // Trabajador encontrado
          } else {
            throw new Error('Unexpected response');
          }
        }),
        catchError((error: HttpErrorResponse) => {
          if (error.status === 404) {
            return throwError(() => new Error('Trabajador no encontrado'));
          }
          return throwError(() => new Error('Error en el servidor'));
        })
      );
  }

}
