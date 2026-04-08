import { Reporte } from '../models/reporte.model';
import { environment } from '../../../environments/environment';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class PermisotrabajoalturasService {
  public dataReadySubject: Subject<boolean> = new Subject<boolean>();

  private _reporte: Reporte | null;

  constructor(private http: HttpClient) {}

  public generateCertificado(idaprendiz: number): void {
    this.http
    .get<Reporte>(`${environment.apiUrl}/permisotrabajoalturas/${idaprendiz}`)
    .subscribe(resp => {
      this._reporte = resp
      this.dataReadySubject.next(true);
    });
  }

  get reporte() {
    return this._reporte;
  }
}
