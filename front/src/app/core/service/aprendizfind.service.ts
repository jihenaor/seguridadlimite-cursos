import { Injectable, inject, signal, computed } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { catchError, finalize, throwError } from 'rxjs';
import { Observable } from 'rxjs';

import { Subject } from 'rxjs';

import { environment } from 'src/environments/environment';
import { Aprendiz } from '../models/aprendiz.model';

interface State {
  aprendizs: Aprendiz[] | null;
  loading: boolean;
}

@Injectable({
  providedIn: 'root',
})
export class AprendizFindService {

  private http2 = inject(HttpClient);

  #state = signal<State>({
    aprendizs: [],
    loading: true,
  })

  public dataReadySubject: Subject<boolean> = new Subject<boolean>();

  private _aprendizs: Aprendiz[] | null;

  public aprendizs2 = computed(() => this.#state().aprendizs);

  constructor(private http: HttpClient) {
  }

  public searchAprendiz(filtro: string): void {
    this.http
    .get<Aprendiz[]>(`${environment.apiUrl}/aprendiz/${filtro}/filtro`)
    .subscribe(resp => {
      this._aprendizs = resp
      this.dataReadySubject.next(true);
    });
  }

  public searchAprendiz2(filtro: string): void {
    this.http2.get<Aprendiz[]>(`${environment.apiUrl}/aprendiz/${filtro}/filtro`)
      .subscribe(resp =>{
        this.#state.set({
          loading: false,
          aprendizs: resp
        })
    });
  }

  public searchAprendizInscripcion(): void {
    this.http
    .get<Aprendiz[]>(`${environment.apiUrl}/aprendiz/inscripcion`)
    .subscribe(resp => {
      this.#state.set({
        loading: false,
        aprendizs: resp
      })

    });
  }

  public searchAprendizInscritos(): void {
    this.#state.set({
      loading: true,
      aprendizs: null
    });

    this.http
      .get<Aprendiz[]>(`${environment.apiUrl}/aprendiz/inscritos`)
      .pipe(
        catchError(error => {
          console.error('Error al obtener aprendices inscritos:', error);
          this.#state.set({
            loading: false,
            aprendizs: null
          });
          return throwError(() => new Error('Error al cargar aprendices inscritos'));
        }),
        finalize(() => {
          if (this.#state().loading) {
            this.#state.update(state => ({...state, loading: false}));
          }
        })
      )
      .subscribe(resp => {
        this.#state.set({
          loading: false,
          aprendizs: resp
        });
      });
  }

  public searchAprendizByIdpermiso(idpermiso: number): Observable<Aprendiz[]> {
    return this.http
      .get<Aprendiz[]>(`${environment.apiUrl}/aprendiz/${idpermiso}/permisotrabajo`);
  }

  public searchAprendizUltimaAsistenciaByIdpermiso(idpermiso: number): Observable<Aprendiz[]> {
    return this.http
      .get<Aprendiz[]>(`${environment.apiUrl}/aprendiz/${idpermiso}/permisotrabajo/ultimaasistencia`);
  }

  public searchAprendizInscritosByIdpermiso(idpermiso: number): Observable<Aprendiz[]> {
    return this.http
      .get<Aprendiz[]>(`${environment.apiUrl}/aprendiz/${idpermiso}/permisotrabajo/inscritos`);
  }

  public updateAprendizIdPermiso(idAprendiz: number, idPermiso: number): Observable<any> {
    return this.http.post<any>(`${environment.apiUrl}/aprendiz/updateIdpermiso`, {
      idAprendiz: idAprendiz,
      idPermiso: idPermiso
    });
  }

  get aprendizs() {
    return this._aprendizs;
  }
}
