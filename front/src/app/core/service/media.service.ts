import { computed, Injectable, inject, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';

/** Respuesta GET `/aprendiz/{id}/foto` (FotoDTO en backend). */
export interface AprendizFotoResponse {
  id?: number;
  base64: string;
  ext?: string;
}

@Injectable({
  providedIn: 'root',
})
export class MediaService {
  private http = inject(HttpClient);

  /** Base64 crudo, `mime;base64,...` o `data:...` según el backend. */
  public fotoObtenida = signal<string | null>(null);
  public fotoExt = signal<string | null>(null);
  public firmaObtenida = signal<string | null>(null);

  /** URL lista para `<img [src]>` a partir de la foto cargada. */
  readonly fotoDataUrl = computed(() => {
    const b = this.fotoObtenida();
    if (!b) {
      return null as string | null;
    }
    if (b.startsWith('data:')) {
      return b;
    }
    // Sin `data:` el navegador trata `image/jpeg;base64,...` como URL relativa (GET enorme → 431).
    if (b.startsWith('image/') && b.includes(';base64,')) {
      return `data:${b}`;
    }
    const rawExt = (this.fotoExt() || 'png').replace(/^\./, '').toLowerCase();
    const mime =
      rawExt === 'jpg' || rawExt === 'jpeg' ? 'image/jpeg' : rawExt === 'webp' ? 'image/webp' : 'image/png';
    return `data:${mime};base64,${b}`;
  });

  clearFoto(): void {
    this.fotoObtenida.set(null);
    this.fotoExt.set(null);
  }

  /**
   * GET `/aprendiz/{id}/foto`. Opcionalmente notifica si la carga fue correcta (p. ej. abrir visor al pulsar «Ver foto»).
   */
  public searchFoto(idtrabajador: number, onDone?: (ok: boolean) => void): void {
    this.clearFoto();
    this.http
      .get<AprendizFotoResponse>(`${environment.apiUrl}/aprendiz/${idtrabajador}/foto`)
      .subscribe({
        next: (resp) => {
          this.fotoObtenida.set(resp.base64);
          this.fotoExt.set(resp.ext ?? null);
          onDone?.(true);
        },
        error: () => {
          this.clearFoto();
          onDone?.(false);
        },
      });
  }

  public searchFirma(idaprendiz: number): void {
    this.firmaObtenida.set(null);
    this.http
      .get<{ idaprendiz: number; base64: string; formato: string }>(`${environment.apiUrl}/aprendiz/${idaprendiz}/firma`)
      .subscribe({
        next: (resp) => {
          this.firmaObtenida.set(resp.base64);
        },
        error: (error) => {
          console.error('Error al cargar la firma:', error);
          this.firmaObtenida.set(null);
        },
      });
  }
}
