import { environment } from './../../../environments/environment';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Subject } from 'rxjs';
import { Aprendiz } from '../models/aprendiz.model';

@Injectable({
  providedIn: 'root',
})
export class AprendizFindIdService {
  public dataReadySubject: Subject<boolean> = new Subject<boolean>();

  private _aprendiz: Aprendiz | null;

  constructor(private http: HttpClient) {}

  public searchAprendizId(id: number): void {
    this.http
    .get<Aprendiz>(`${environment.apiUrl}/aprendiz/${id}`)
    .subscribe(resp => {
      this._aprendiz = resp
      this.dataReadySubject.next(true);
    });
  }

  get aprendiz() {
    return this._aprendiz;
  }
}
