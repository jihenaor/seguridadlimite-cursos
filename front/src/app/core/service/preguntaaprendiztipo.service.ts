import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Subject, map } from 'rxjs';

import { environment } from '../../../environments/environment';

import { Pregunta } from '../models/pregunta.model';

@Injectable({
  providedIn: 'root'
})

export class Preguntaaprendiztipo {
  public dataReadySubject: Subject<boolean> = new Subject<boolean>();

  private _preguntas: Pregunta[] | null;
  private _groupedPreguntas: Record<string, Record<string, Pregunta[]>> = {};

  constructor(private http: HttpClient) {}

  public search(idaprendiz: string, tipoEvaluacion: string, numero: string): void {
    this.http
    .get<Pregunta[]>(`${environment.apiUrl}/evaluacion/${idaprendiz}/${tipoEvaluacion}/${numero}/tipoevaluacion`)
    .subscribe(resp => {
      this._preguntas = resp

      this._preguntas.map((pregunta) => {
          if (pregunta.opcionesrespuesta) {
            pregunta.opcionesrespuestaArray = pregunta.opcionesrespuesta.split(',');
          }
      });

      this.groupByAgrupadores(this.preguntas)

      this.dataReadySubject.next(true);
    });
  }

  private groupByAgrupadores(preguntas: Pregunta[]): void {
    const grouped: Record<string, Record<string, Pregunta[]>> = {};

    preguntas.forEach(pregunta => {
      if (!grouped[pregunta.agrupador1]) {
        grouped[pregunta.agrupador1] = {};
      }
      if (!grouped[pregunta.agrupador1][pregunta.agrupador2]) {
        grouped[pregunta.agrupador1][pregunta.agrupador2] = [];
      }
      if (!grouped[pregunta.agrupador1][pregunta.agrupador2][pregunta.agrupador3]) {
        grouped[pregunta.agrupador1][pregunta.agrupador2][pregunta.agrupador3] = [];
      }
      grouped[pregunta.agrupador1][pregunta.agrupador2][pregunta.agrupador3].push(pregunta);
    });

    this._groupedPreguntas = grouped;
  }
  get preguntas() {
    return this._preguntas;
  }

  get groupedPreguntas() {
    return this._groupedPreguntas;
  }
}
