import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { PermisoTrabajoAlturas } from '../models/permiso-trabajo.interface';

@Injectable({
  providedIn: 'root'
})
export class PermisoTrabajoService {
  private apiUrl = `${environment.apiUrl}/permisos`;

  constructor(private http: HttpClient) { }

  /**
   * Consulta un permiso de trabajo en alturas por su ID
   * @param idPermiso ID del permiso a consultar
   * @returns Observable con los datos del permiso
   */
  consultarPermiso(idPermiso: number): Observable<PermisoTrabajoAlturas> {
    return this.http.get<PermisoTrabajoAlturas>(`${this.apiUrl}/${idPermiso}/consultar`);
  }
}
