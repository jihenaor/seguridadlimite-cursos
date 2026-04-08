import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { Nivel } from '../models/nivel.model';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';


@Injectable({
  providedIn: 'root', // Esto garantiza que el servicio esté disponible en toda la aplicación
})
export class NivelsService {
  _dataChange: BehaviorSubject<Nivel[]> = new BehaviorSubject<Nivel[]>([]);
  private _data: Nivel[] = [];

  get dataChange(): Observable<Nivel[]> {
    return this._dataChange.asObservable();
  }

  dialogData: any;
  constructor(private httpClient: HttpClient) {}
  get data(): Nivel[] {
    return this._dataChange.value;
  }
  getDialogData() {
    return this.dialogData;
  }

  getNivels(): Observable<Nivel[]> {
    return this.httpClient.get<Nivel[]>(`${environment.apiUrl}` + '/nivel');
  }

  getNivelActivos(): Observable<Nivel[]> {
    return this.httpClient.get<Nivel[]>(`${environment.apiUrl}` + '/nivel/activos');
  }

  getNivelActivosFecha(): Observable<Nivel[]> {
    return this.httpClient.get<Nivel[]>(`${environment.apiUrl}` + '/nivel/activosfecha');
  }

  getNivelActivosinscripcion(): Observable<Nivel[]> {
    return this.httpClient.get<Nivel[]>(`${environment.apiUrl}` + '/nivel/activosinscripcion');
  }
}
