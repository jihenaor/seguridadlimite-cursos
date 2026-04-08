import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Subject, map } from 'rxjs';

import { environment } from './../../../environments/environment';

import { Pregunta } from '../../core/models/pregunta.model';
import { RespuestaEncuestaSatisfaccion } from '../../core/models/respuestaencuestasatisfaccion.model';

@Injectable({
  providedIn: 'root'
})

export class PreguntasatisfaccionclienteService {
  public dataReadySubject: Subject<boolean> = new Subject<boolean>();

  private _preguntas: Pregunta[] | null;

  private _comentariosencuesta: string;
  private _activacomentarios: boolean;

  private _groupedPreguntas: Record<string, Record<string, Pregunta[]>> = {};


  constructor(private http: HttpClient) {}

  public searchEncuestaSatisfaccionCliente(idaprendiz: string): void {
    this.http
    .get<RespuestaEncuestaSatisfaccion>(`${environment.apiUrl}/evaluacion/${idaprendiz}/satisfaccioncliente`)
    .subscribe(resp => {

      this._preguntas = resp.preguntas
      this._comentariosencuesta = resp.comentariosencuesta
      this._activacomentarios = resp.activacomentarios

      this._preguntas.map((pregunta) => {
          if (pregunta.opcionesrespuesta) {
            pregunta.opcionesrespuestaArray = pregunta.opcionesrespuesta.split(',');
          }
      });

      this.groupByAgrupadores(this._preguntas)

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

  get comentariosencuesta() {
    return this._comentariosencuesta;
  }

  get activacomentarios() {
    return this._activacomentarios;
  }

}
