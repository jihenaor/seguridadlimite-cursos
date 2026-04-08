import { HttpClient } from '@angular/common/http';
import { Injectable, signal } from '@angular/core';
import { firstValueFrom } from 'rxjs';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class RegistrarContinuaAprendizajeService {
  private loadingState = signal<boolean>(false);
  private errorMessage = signal<string | null>(null);

  public loading = this.loadingState.asReadonly();
  public error = this.errorMessage.asReadonly();

  private readonly apiUrl = `${environment.apiUrl}/aprendiz`;

  constructor(private http: HttpClient) {}

  async registrarContinuacionAprendizaje(idAprendiz: number): Promise<void> {
    try {
      this.loadingState.set(true);
      this.errorMessage.set(null);

      await firstValueFrom(
        this.http.post<void>(
          `${this.apiUrl}/continuaaprendizaje`,
          idAprendiz,
          {
            headers: {
              'Content-Type': 'application/json'
            }
          }
        )
      );
    } catch (error) {
      console.error('Error al registrar continuación:', error);
      const mensaje = error.error?.message || 'Error al registrar la continuación del aprendizaje';
      this.errorMessage.set(mensaje);
      throw new Error(mensaje);
    } finally {
      this.loadingState.set(false);
    }
  }
}
