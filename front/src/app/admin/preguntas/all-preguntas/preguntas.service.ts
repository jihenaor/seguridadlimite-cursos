import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { Pregunta } from '../../../core/models/pregunta.model';

import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { environment } from 'src/environments/environment';
@Injectable()
export class PreguntasService {

  private _preguntas: Pregunta[] | null;
  private _preguntasFiltered: Pregunta[] | null;

  constructor(private httpClient: HttpClient) {}

  getPreguntasNivelTipoEvaluacion(idnivel: string, tipoevaluacion: string): void {
    const nameService = '/preguntarniveltipoevaluacion/' + idnivel + '/' + tipoevaluacion;

    this.httpClient.get<Pregunta[]>(`${environment.apiUrl}` + nameService).subscribe(
      data => {
        this._preguntas = data;
        this._preguntasFiltered = data;
      }
    );
  }

  getPreguntasGrupo(idgrupo: string): void {
    let nameService: string;

    nameService = '/pregunta/' + idgrupo + '/grupo';
    this.httpClient.get<Pregunta[]>(`${environment.apiUrl}` + nameService).subscribe(
      data => {
        this._preguntas = data;
        this._preguntasFiltered = data;
      }
    );
  }

  get preguntas() {
    return this._preguntasFiltered;
  }

  filter(filter: string) {
    if (!filter || filter.length === 0) {
      this._preguntasFiltered = this._preguntas;
    } else {
      this._preguntasFiltered = this._preguntasFiltered = this.preguntas
      .filter((pregunta: Pregunta) => {
        const searchStr = (pregunta.agrupador1 || '').toLowerCase();
        const nitStr = (pregunta.agrupador2 || '').toLowerCase();
        const searchTerm = filter.toLowerCase();
        return searchStr.indexOf(searchTerm) !== -1 || nitStr.indexOf(searchTerm) !== -1;
      });
    }
  }

}
