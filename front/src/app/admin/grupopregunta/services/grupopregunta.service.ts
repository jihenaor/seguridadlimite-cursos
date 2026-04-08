import { Grupopregunta } from './../../../core/models/grupopregunta.model';
import { environment } from './../../../../environments/environment';
import { Injectable } from '@angular/core';

import { Subject } from 'rxjs';
import { HttpClient } from '@angular/common/http';


@Injectable({
  providedIn: 'root'
})
export class GrupopreguntaService {
  public dataReadySubject: Subject<boolean> = new Subject<boolean>();

  private _grupopreguntas: Grupopregunta[] | null;

  constructor(private http: HttpClient) {}

  public getGrupopregunta(): void {
    this.http
    .get<Grupopregunta[]>(`${environment.apiUrl}/grupopregunta`)
    .subscribe(resp => {
      this._grupopreguntas = resp
      this.dataReadySubject.next(true);
    });
  }

  get grupopreguntas() {
    return this._grupopreguntas;
  }
}
