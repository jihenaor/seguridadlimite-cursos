import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { catchError, finalize } from 'rxjs/operators';
import { throwError } from 'rxjs';

import { Subject } from 'rxjs';

import { environment } from '../../../environments/environment';

import { Pregunta } from '../../core/models/pregunta.model';
import { Grupoevaluacion } from 'src/app/core/models/grupoevaluacion.model';

@Injectable({
  providedIn: 'root',
})
export class PreguntasEvaluacionTeacherService {
  public dataReadySubject: Subject<boolean> = new Subject<boolean>();

  private _grupoevaluacion: Grupoevaluacion[] | null;

  constructor(private http: HttpClient) {}

  public searchPreguntasaprendiz(idaprendiz: string): void {

    this.http
      .get<Grupoevaluacion[]>(`${environment.apiUrl}/evaluacion/${idaprendiz}/practica`)
      .pipe(
        finalize(() => {
          this.dataReadySubject.next(true);
        })
      )
      .subscribe(resp => {
        this._grupoevaluacion = resp;

        if (this._grupoevaluacion === null || this._grupoevaluacion.length === 0) {
          alert(`No existen preguntas para este aprendiz ${idaprendiz}`);
        }
      });
  }

  get grupoevaluacion() {
    return this._grupoevaluacion;
  }
}
