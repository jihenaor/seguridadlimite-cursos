import { Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { catchError, tap } from 'rxjs/operators';
import { Observable, of } from 'rxjs';
import { PermisoTrabajoAlturas } from '../models/permiso-trabajo.interface';

@Injectable({
  providedIn: 'root',
})
export class PermisoTrabajoSignalsService {
  private apiUrl = `${environment.apiUrl}/permisos-trabajo-alturas`;

  // Signals para los datos
  private _permisos = signal<PermisoTrabajoAlturas[]>([]);
  private _permisoSeleccionado = signal<PermisoTrabajoAlturas | null>(null);
  private _cargando = signal<boolean>(false);
  private _error = signal<string | null>(null);

  // Exposición pública de los signals (solo lectura)
  public permisos = this._permisos.asReadonly();
  public permisoSeleccionado = this._permisoSeleccionado.asReadonly();
  public cargando = this._cargando.asReadonly();
  public error = this._error.asReadonly();

  constructor(private http: HttpClient) {}

  /**
   * Consulta permisos de trabajo por rango de fechas
   * @param fechaInicio Fecha de inicio en formato YYYY-MM-DD
   * @param fechaFin Fecha de fin en formato YYYY-MM-DD
   */
  consultarPorFechas(fechaInicio: string, fechaFin: string): void {
    this._cargando.set(true);
    this._error.set(null);

    this.http.get<PermisoTrabajoAlturas[]>(`${this.apiUrl}/por-fechas?fechaInicio=${fechaInicio}&fechaFin=${fechaFin}`)
      .pipe(
        tap(permisos => {
          // Asegurarse de que todos los permisos tengan las propiedades necesarias
          const permisosNormalizados = permisos.map(permiso => this.normalizarPermiso(permiso));
          this._permisos.set(permisosNormalizados);
          this._cargando.set(false);
        }),
        catchError(err => {
          console.error('Error al consultar permisos por fechas:', err);
          this._error.set('Error al cargar los permisos de trabajo');
          this._cargando.set(false);
          return of([]);
        })
      )
      .subscribe();
  }

  consultarPorFecha(fecha: string): void {
    this._cargando.set(true);
    this._error.set(null);

    this.http.get<PermisoTrabajoAlturas[]>(`${this.apiUrl}/por-fecha?fecha=${fecha}`)
      .pipe(
        tap(permisos => {
          if (!Array.isArray(permisos)) {
            throw new Error('La respuesta no es un array');
          }
          // Asegurarse de que todos los permisos tengan las propiedades necesarias
          const permisosNormalizados = permisos.map(permiso => this.normalizarPermiso(permiso));
          this._permisos.set(permisosNormalizados);
          this._cargando.set(false);
        }),
        catchError(err => {
          console.error('Error al consultar permisos por fechas:', err);
          this._error.set('Error al cargar los permisos de trabajo');
          this._permisos.set([]);
          this._cargando.set(false);
          return of([]);
        })
      )
      .subscribe();
  }

  /**
   * Consulta permisos de trabajo por código ministerio
   * @param codigoministerio Código ministerio para filtrar
   */
  consultarPorCodigoMinisterio(codigoministerio: string): void {
    this._cargando.set(true);
    this._error.set(null);

    this.http.get<PermisoTrabajoAlturas[]>(`${this.apiUrl}/por-codigo-ministerio?codigoministerio=${codigoministerio}`)
      .pipe(
        tap(permisos => {
          if (!Array.isArray(permisos)) {
            throw new Error('La respuesta no es un array');
          }
          // Asegurarse de que todos los permisos tengan las propiedades necesarias
          const permisosNormalizados = permisos.map(permiso => this.normalizarPermiso(permiso));
          this._permisos.set(permisosNormalizados);
          this._cargando.set(false);
        }),
        catchError(err => {
          console.error('Error al consultar permisos por código ministerio:', err);
          this._error.set('Error al cargar los permisos de trabajo');
          this._permisos.set([]);
          this._cargando.set(false);
          return of([]);
        })
      )
      .subscribe();
  }

  /**
   * Obtiene un permiso por su ID y lo establece como seleccionado
   * @param id ID del permiso
   */
  obtenerPermisoPorId(id: number): void {
    this._cargando.set(true);
    this._error.set(null);
    this.http.get<PermisoTrabajoAlturas>(`${this.apiUrl}/${id}`)
      .pipe(
        tap(permiso => {
          this._permisoSeleccionado.set(this.normalizarPermiso(permiso));
          this._cargando.set(false);
        }),
        catchError(err => {
          console.error('Error al obtener permiso por ID:', err);
          this._error.set('Error al cargar el detalle del permiso');
          this._cargando.set(false);
          return of(null);
        })
      )
      .subscribe();
  }


  /**
   * Normaliza un objeto PermisoTrabajoAlturas para asegurar que tenga todas las propiedades necesarias
   * @param permiso Objeto PermisoTrabajoAlturas a normalizar
   * @returns Objeto PermisoTrabajoAlturas normalizado
   */
  private normalizarPermiso(permiso: PermisoTrabajoAlturas): PermisoTrabajoAlturas {
    if (!permiso) {
      throw new Error('El permiso no puede ser null');
    }

    return {
      ...permiso,
      idPermiso: permiso.idPermiso,
      nombrenivel: permiso.nombrenivel || '',
      validodesde: permiso.validodesde || '',
      validohasta: permiso.validohasta || '',
      verificacionSeguridadSocial: permiso.verificacionSeguridadSocial || false,
      certificadoAptitudMedica: permiso.certificadoAptitudMedica || false,
      certificadoCompetencia: permiso.certificadoCompetencia || false,
      condicionSaludTrabajador: permiso.condicionSaludTrabajador || false,
      tiposTrabajo: permiso.tiposTrabajo || [],
      seleccionado: false
    };
  }

  /**
   * Crea un nuevo permiso de trabajo
   * @param permiso Datos del permiso a crear
   * @returns Observable con el permiso creado
   */
  crearPermiso(permiso: PermisoTrabajoAlturas): Observable<PermisoTrabajoAlturas> {
    this._cargando.set(true);
    this._error.set(null);

    return this.http.post<PermisoTrabajoAlturas>(this.apiUrl, permiso)
      .pipe(
        tap(nuevoPermiso => {
          // Actualizar la lista de permisos añadiendo el nuevo
          const permisosActuales = this._permisos();
          this._permisos.set([...permisosActuales, nuevoPermiso]);
          this._cargando.set(false);
        }),
        catchError(err => {
          console.error('Error al crear permiso:', err);
          this._error.set('Error al crear el permiso de trabajo');
          this._cargando.set(false);
          throw err;
        })
      );
  }

  /**
   * Actualiza un permiso existente
   * @param id ID del permiso
   * @param permiso Datos actualizados
   * @returns Observable con el permiso actualizado
   */
  actualizarPermiso(id: number, permiso: PermisoTrabajoAlturas): Observable<PermisoTrabajoAlturas> {
    this._cargando.set(true);
    this._error.set(null);

    return this.http.put<PermisoTrabajoAlturas>(`${this.apiUrl}/${id}`, permiso)
      .pipe(
        tap(permisoActualizado => {
          // Actualizar la lista de permisos
          const permisosActuales = this._permisos();
          const index = permisosActuales.findIndex(p => p.idPermiso === id);

          if (index !== -1) {
            const nuevosPermisos = [...permisosActuales];
            nuevosPermisos[index] = permisoActualizado;
            this._permisos.set(nuevosPermisos);
          }

          this._cargando.set(false);
        }),
        catchError(err => {
          console.error('Error al actualizar permiso:', err);
          this._error.set('Error al actualizar el permiso de trabajo');
          this._cargando.set(false);
          throw err;
        })
      );
  }


  actualizarFechasActivas(id: number, 
    fechas: { validodesde: string; validohasta: string }): void {
    this._cargando.set(true);
    this._error.set(null);

    this.http.put<{ message: string }>(`${this.apiUrl}/${id}/fechas-activo`, fechas)
      .pipe(
        tap(response => {
          // Actualizar la lista de permisos localmente
          const permisosActuales = this._permisos();
          const index = permisosActuales.findIndex(p => p.idPermiso === id);

          if (index !== -1) {
            const nuevosPermisos = [...permisosActuales];
            nuevosPermisos[index] = { ...nuevosPermisos[index], ...fechas };
            this._permisos.set(nuevosPermisos);
          }

          this._cargando.set(false);
          console.log(response.message); // Mostrar mensaje de éxito
        }),
        catchError(err => {
          console.error('Error al actualizar fechas activas:', err);
          this._error.set(err?.error?.message || 'Error al actualizar las fechas activas');
          this._cargando.set(false);
          return of(null);
        })
      )
      .subscribe();
  }

  eliminarPermiso(id: number): Observable<any> {
    this._cargando.set(true);
    this._error.set(null);

    return this.http.delete(`${this.apiUrl}/${id}`)
      .pipe(
        tap(() => {
          // Eliminar el permiso de la lista
          const permisosActuales = this._permisos();
          this._permisos.set(permisosActuales.filter(p => p.idPermiso !== id));
          this._cargando.set(false);
        }),
        catchError(err => {
          console.error('Error al eliminar permiso:', err);
          this._error.set('Error al eliminar el permiso de trabajo');
          this._cargando.set(false);
          throw err;
        })
      );
  }

  /**
   * Limpia el permiso seleccionado
   */
  limpiarPermisoSeleccionado(): void {
    this._permisoSeleccionado.set(null);
  }

  /**
   * Limpia el mensaje de error
   */
  limpiarError(): void {
    this._error.set(null);
  }

  
}
