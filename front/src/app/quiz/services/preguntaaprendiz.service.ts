import { RespuestaEvaluacion } from './../interfaces/quiz.interfaces';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Subject } from 'rxjs';

import { environment } from 'src/environments/environment';


@Injectable()
export class PreguntaaprendizService {
  public dataReadySubject: Subject<boolean> = new Subject<boolean>();

  private _respuestaEvaluacion: RespuestaEvaluacion[] | null;

  constructor(private http: HttpClient) {}

  public searchPreguntasaprendizEvaluacionTeorica(idaprendiz: number): void {
    this.http
    .get<RespuestaEvaluacion[]>(`${environment.apiUrl}/evaluacion/${idaprendiz}/teorica`)
    .subscribe(resp => {
      this._respuestaEvaluacion = resp
      this.dataReadySubject.next(true);
    });
  }

  public searchPreguntasaprendizIngreso(idaprendiz: number): void {
    this.http
    .get<RespuestaEvaluacion[]>(`${environment.apiUrl}/evaluacion/${idaprendiz}/ingreso`)
    .subscribe(resp => {
      this._respuestaEvaluacion = resp
      this.dataReadySubject.next(true);
    });
  }

  get respuestaEvaluacion() {
    return this._respuestaEvaluacion;
  }

}
