import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, throwError } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Aprendiz } from '../../../core/models/aprendiz.model';
import { environment } from '../../../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class AprendizDocumentosService {
  private dataChange: BehaviorSubject<Aprendiz> = new BehaviorSubject<Aprendiz>(null);

  // Propiedad pública que expone el Observable
  public readonly aprendiz$: Observable<Aprendiz> = this.dataChange.asObservable();

  constructor(private httpClient: HttpClient) {}

  /**
   * Obtiene el aprendiz por su ID y actualiza el BehaviorSubject.
   * @param idaprendiz Identificador del aprendiz.
   */
  getAprendiz(idaprendiz: number): void {
    this.httpClient
      .get<Aprendiz>(`${environment.apiUrl}/aprendiz/${idaprendiz}/documentos`)
      .subscribe((data) => {
        this.dataChange.next(data);
      });
  }

  get aprendiz(): Aprendiz {
    return this.dataChange.value;
  }
}
