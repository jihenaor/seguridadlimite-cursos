import { Injectable, inject, signal, computed } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { tap, catchError } from 'rxjs/operators';
import { of } from 'rxjs';
import { Trabajador } from '../models/trabajador.model';


interface State {
  trabajador: Trabajador | null;
  loading: boolean;
  error: string | null;
}

@Injectable({
  providedIn: 'root'
})
export class TrabajadorService {
  private http = inject(HttpClient);
  private apiUrl = environment.apiUrl;

  // Estado inicial
  private state = signal<State>({
    trabajador: null,
    loading: false,
    error: null
  });

  // Signals públicos computados
  trabajador = computed(() => this.state().trabajador);
  loading = computed(() => this.state().loading);
  error = computed(() => this.state().error);

  updateTrabajador(id: number, trabajador: Partial<Trabajador>) {
    this.state.update(state => ({ ...state, loading: true, error: null }));

    return this.http.put<Trabajador>(`${this.apiUrl}/trabajador/${id}`, trabajador).pipe(
      tap(updatedTrabajador => {
        this.state.update(state => ({
          ...state,
          trabajador: updatedTrabajador,
          loading: false
        }));
      }),
      catchError(error => {
        this.state.update(state => ({
          ...state,
          error: 'Error al actualizar el trabajador',
          loading: false
        }));
        return of(error);
      })
    );
  }
}
