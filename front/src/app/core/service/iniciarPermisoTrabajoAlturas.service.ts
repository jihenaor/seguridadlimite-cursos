import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Nivel } from '../models/nivel.model';
import { environment } from '../../../environments/environment';

/** Cuerpo de error HTTP 409 del backend al detectar solapamiento de permisos. */
export interface PermisoSolapamientoErrorBody {
  code?: string;
  message?: string;
  conflictos?: PermisoSolapamientoConflicto[];
}

export interface PermisoSolapamientoConflicto {
  idNivel: number;
  idPermisoExistente: number;
  permisoExistenteValidoDesde: string;
  permisoExistenteValidoHasta: string;
  solicitudValidoDesde: string;
  solicitudValidoHasta: string;
}

@Injectable({
  providedIn: 'root'
})
export class IniciarPermisoTrabajoAlturasService {

  constructor(private http: HttpClient) {}

  startWorkPermissions(niveles: Nivel[], forzarSolapamiento = false): Observable<unknown> {
    const params = new HttpParams().set('forzarSolapamiento', String(forzarSolapamiento));
    return this.http.post(`${environment.apiUrl}/permisos/start-work-permissions`, niveles, { params });
  }
}
