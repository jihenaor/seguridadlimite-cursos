import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { catchError, finalize } from 'rxjs/operators';
import { throwError } from 'rxjs';

import { Subject } from 'rxjs';

import { environment } from './../../../environments/environment';
import { Aprendiz } from '../../core/models/aprendiz.model';


@Injectable({
  providedIn: 'root',
})
export class AprendizGrupoTeacherService {
  public dataReadySubject: Subject<boolean> = new Subject<boolean>();

  private _aprendizs: Aprendiz[] | null;

  constructor(private http: HttpClient) {}

  public searchAprendices(idgrupo: number): void {
    this.http
      .get<Aprendiz[]>(`${environment.apiUrl}/aprendiz?idgrupo=${idgrupo}`)
      .pipe(
        finalize(() => {
          this.dataReadySubject.next(true);
        })
      )
      .subscribe(resp => {
        this._aprendizs = resp;

        if (this._aprendizs === null || this._aprendizs.length === 0) {
          alert(`No existen aprendices para este grupo ${idgrupo}`);
        }
      });
  }

  public searchAprendicessininscrpcion(): void {
    this.http
      .get<Aprendiz[]>(`${environment.apiUrl}/aprendiz/inscripcion`)
      .pipe(
        catchError(error => {
          console.error('Error al buscar aprendices sin inscripción:', error);
          alert('Ha ocurrido un error al buscar aprendices sin inscripción');
          return throwError(() => error);
        }),
        finalize(() => {
          this.dataReadySubject.next(true);
        })
      )
      .subscribe({
        next: (resp) => {
          this._aprendizs = resp;
          if (this._aprendizs === null || this._aprendizs.length === 0) {
            alert('No existen aprendices sin inscripción');
          }
        },
        error: () => {
          this._aprendizs = null;
        }
      });
  }

  get aprendiz() {
    return this._aprendizs;
  }
}
