import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { RegistroInformeInscritosDto } from '../../../core/models/aprendicesinscritos';

@Injectable({
  providedIn: 'root',
})
export class AprendicesInscritosService {

  private _data: RegistroInformeInscritosDto[] | null;

  constructor(private httpClient: HttpClient) {}

  getPagospendientesempresa(fechaInicial: String, fechaFinal: String, aprendizDetalle): void {

    this.httpClient.get<RegistroInformeInscritosDto[]>(`${environment.apiUrl}/aprendiz/informeaprendicesinscritos/${fechaInicial}/${fechaFinal}/${aprendizDetalle}`).subscribe(
      resp => {
        this._data = resp;
      },
    );
  }

  get data() {
    return this._data;
  }
}
