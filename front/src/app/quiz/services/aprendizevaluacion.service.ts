import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { finalize } from 'rxjs/operators';
import { Subject } from 'rxjs';

import { environment } from 'src/environments/environment';
import { Aprendiz } from 'src/app/core/models/aprendiz.model';

@Injectable({
  providedIn: 'root',
})
export class AprendizEvaluacionService {
  public dataReadySubjectAprendizEvaluacion: Subject<boolean> = new Subject<boolean>();

  private _aprendiz: Aprendiz | null;

  constructor(private http: HttpClient) {}

  public searchAprendizevaluacion(numerodocumento: string, tipoevaluacion: string): void {
    this._aprendiz = null;

    this.http
      .get<Aprendiz>(`${environment.apiUrl}/aprendiz/${numerodocumento}/evaluacion${tipoevaluacion}`)
      .pipe(
        finalize(() => {
          this.dataReadySubjectAprendizEvaluacion.next(true);
        })
      )
      .subscribe(resp => {
        this._aprendiz = resp;

        if (this._aprendiz === null) {
          alert('AprEv - No se encontró aprendiz con esta identificación (' + numerodocumento + ')');
        } else {
          sessionStorage.setItem('idaprendiz', this.aprendiz.id + '');
          sessionStorage.setItem('numerodocumento', numerodocumento);
          sessionStorage.setItem('nombreaprendiz',
            this.aprendiz.trabajador.primernombre + ' ' +
            this.aprendiz.trabajador.segundonombre + ' ' +
            this.aprendiz.trabajador.primerapellido + ' ' +
            this.aprendiz.trabajador.segundoapellido);
        }
      });
  }

  get aprendiz() {
    return { ...this._aprendiz };
  }
}
