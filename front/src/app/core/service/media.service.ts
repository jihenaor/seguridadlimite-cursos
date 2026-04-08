import { Injectable, inject, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class MediaService {
  private http = inject(HttpClient);
  public fotoObtenida = signal<string | null>(null); // Foto base64 o null
  public firmaObtenida = signal<string | null>(null); // Firma base64 o null

  public searchFoto(idtrabajador: number): void {
    this.fotoObtenida.set(null); // Reinicia la señal antes de la llamada
    this.http
      .get<{ base64: string }>(`${environment.apiUrl}/aprendiz/${idtrabajador}/foto`)
      .subscribe({
        next: (resp) => {
          this.fotoObtenida.set(resp.base64); // Actualiza la señal con la foto base64
        },
        error: (error) => {
          console.error('Error al cargar la foto:', error);
          this.fotoObtenida.set(null); // En caso de error, se mantiene null
        },
      });
  }

  public searchFirma(idaprendiz: number): void {
    this.firmaObtenida.set(null); // Reinicia la señal antes de la llamada
    this.http
      .get<{ idaprendiz: number; base64: string; formato: string }>(`${environment.apiUrl}/aprendiz/${idaprendiz}/firma`)
      .subscribe({
        next: (resp) => {
          this.firmaObtenida.set(resp.base64); // Actualiza la señal con la firma base64
        },
        error: (error) => {
          console.error('Error al cargar la firma:', error);
          this.firmaObtenida.set(null); // En caso de error, se mantiene null
        },
      });
  }
}
