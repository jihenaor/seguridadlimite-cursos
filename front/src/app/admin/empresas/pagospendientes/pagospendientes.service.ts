import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Pagopendienteempresa } from './../../../core/models/pagopendienteempresa.model';

@Injectable({
  providedIn: 'root',
})
export class PagopendienteempresaService {

  private _data: Pagopendienteempresa[] | null;

  constructor(private httpClient: HttpClient) {}

  getPagospendientesempresa(): void {
    this.httpClient.get<Pagopendienteempresa[]>(`${environment.apiUrl}` + '/aprendiz/pagopendienteempresa').subscribe(
      data => {
        this._data = data;
      },
    );
  }

  get data() {
    return this._data;
  }
}
