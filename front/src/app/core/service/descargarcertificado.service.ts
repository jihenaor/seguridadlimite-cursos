import { Reporte } from '../models/reporte.model';
import { environment } from '../../../environments/environment';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class DescargarCertificadoService {
  constructor(private http: HttpClient) {}

  public downloadCertificado(idaprendiz: number): Observable<Reporte> {
    return this.http.get<Reporte>(`${environment.apiUrl}/aprendiz/${idaprendiz}/imagencertificado`);
  }
}
