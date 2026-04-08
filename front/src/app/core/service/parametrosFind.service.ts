import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Subject } from 'rxjs';

import { environment } from 'src/environments/environment';
import { Parametros } from '../models/parametros.model';

@Injectable({
  providedIn: 'root',
})
export class ParametrosFindService {
  public dataReadySubject: Subject<boolean> = new Subject<boolean>();

  private _parametros: Parametros | null;

  constructor(private http: HttpClient) {}

  public get(): void {
    this.http
    .get<Parametros>(`${environment.apiUrl}/parametros`)
    .subscribe(resp => {
      this._parametros = resp
    });
  }

  get parametros() {
    return this._parametros;
  }

}
