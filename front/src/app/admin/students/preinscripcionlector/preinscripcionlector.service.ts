import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { TrabajadorInscripcion } from '../../../core/models/trabajador-inscripcion.model';

@Injectable({
  providedIn: 'root'
})
export class PreinscripcionlectorService {
  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) { }

  getTrabajadorInscripcion(numerodocumento: string): Observable<TrabajadorInscripcion> {
    return this.http.get<TrabajadorInscripcion>(`${this.apiUrl}/trabajadorinscripcion/${numerodocumento}`);
  }
}
