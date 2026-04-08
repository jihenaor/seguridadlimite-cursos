import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { PermisoTrabajoAlturas } from '../models/permiso-trabajo.interface';

@Injectable({
  providedIn: 'root',
})
export class PermisoTrabajoAlturasService {
  private apiUrl = `${environment.apiUrl}/permisos-trabajo-alturas`;

  constructor(private http: HttpClient) {}

  /**
   * Obtiene todos los permisos de trabajo en alturas
   */
  getPermisos(): Observable<PermisoTrabajoAlturas[]> {
    return this.http.get<PermisoTrabajoAlturas[]>(this.apiUrl);
  }

  /**
   * Obtiene un permiso de trabajo en alturas por su ID
   * @param id ID del permiso
   */
  getPermisoById(id: number): Observable<PermisoTrabajoAlturas> {
    return this.http.get<PermisoTrabajoAlturas>(`${this.apiUrl}/${id}`);
  }

  /**
   * Actualiza un permiso de trabajo en alturas existente
   * @param id ID del permiso
   * @param permiso Datos actualizados del permiso
   */
  actualizarPermiso(id: number, permiso: PermisoTrabajoAlturas): Observable<PermisoTrabajoAlturas> {
    return this.http.put<PermisoTrabajoAlturas>(`${this.apiUrl}/${id}`, permiso);
  }

  /**
   * Elimina un permiso de trabajo en alturas
   * @param id ID del permiso a eliminar
   */
  eliminarPermiso(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`);
  }

  /**
   * Obtiene permisos de trabajo filtrados por criterios
   * @param filtros Objeto con los criterios de filtrado
   */
  getPermisosFiltrados(filtros: {tipoId?: number, fechaDesde?: string, fechaHasta?: string}): Observable<PermisoTrabajoAlturas[]> {
    // Construir parámetros de consulta basados en los filtros
    let queryParams = '';
    if (filtros) {
      const params: string[] = [];
      if (filtros.tipoId) params.push(`tipoId=${filtros.tipoId}`);
      if (filtros.fechaDesde) params.push(`fechaDesde=${filtros.fechaDesde}`);
      if (filtros.fechaHasta) params.push(`fechaHasta=${filtros.fechaHasta}`);

      if (params.length > 0) {
        queryParams = '?' + params.join('&');
      }
    }

    return this.http.get<PermisoTrabajoAlturas[]>(`${this.apiUrl}/filtrar${queryParams}`);
  }
}
