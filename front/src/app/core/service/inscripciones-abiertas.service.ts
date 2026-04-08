import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class InscripcionesAbiertasService {
  private apiUrl = `${environment.apiUrl}/permisos/inscripciones-abiertas`;

  constructor(private http: HttpClient) { }

  verificarInscripcionesAbiertas(): Observable<boolean> {
    return this.http.get<boolean>(this.apiUrl);
  }
}