import { Injectable, signal, computed } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { catchError, finalize, throwError } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Aprendiz } from '../models/aprendiz.model';

interface State {
  aprendizs: Aprendiz[] | null;
  loading: boolean;
}

@Injectable({
  providedIn: 'root',
})
export class AprendizInscritoFindService {
  #state = signal<State>({
    aprendizs: [],
    loading: false,
  });

  aprendizs = computed(() => this.#state().aprendizs);
  loading = computed(() => this.#state().loading);

  constructor(private http: HttpClient) {}

  /**
   * Consulta el listado de aprendices inscritos
   */
  public searchAprendizInscritos(): void {
    this.#fetchData(`${environment.apiUrl}/aprendiz/inscritoshoy`);
  }

  /**
   * Consulta los aprendices con filtro personalizado
   * @param filtro El filtro a aplicar en la búsqueda
   */
  public searchAprendizConFiltro(filtro: string): void {
    this.#fetchData(`${environment.apiUrl}/aprendiz/${filtro}/filtro`);
  }

  /**
   * Método privado para manejar la lógica común de obtención de datos
   * @param endpoint URL del servicio a consultar
   */
    #fetchData(endpoint: string): void {
    this.#setLoadingState(true);

    this.http
      .get<Aprendiz[]>(endpoint)
      .pipe(
        catchError(error => {
          console.error('Error al obtener datos:', error);
          this.#state.set({ loading: false, aprendizs: null });
          return throwError(() => new Error('Error al cargar datos'));
        }),
        finalize(() => this.#setLoadingState(false))
      )
      .subscribe(resp => {
        this.#state.set({ loading: false, aprendizs: resp });
      });
  }

  #setLoadingState(isLoading: boolean): void {
    this.#state.update(state => ({ ...state, loading: isLoading }));
  }
}
