import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from './../../../../environments/environment';

import { InformeEncuestaSatisfaccionGrupo } from '../../../core/models/informeencuestasatisfacciongrupo.model';

@Injectable({
  providedIn: 'root'
})
export class ConsultaestadisticasatisfaccionclienteService {

  private _informeEncuestaSatisfaccionGrupo: InformeEncuestaSatisfaccionGrupo | null;

  constructor(private http: HttpClient) {}

  public searchInformeEncuestaSatisfaccionGrupo(idgrupo: number): void {
    this.http
    .get<InformeEncuestaSatisfaccionGrupo>(`${environment.apiUrl}/evaluacion/${idgrupo}/grupo`)
    .subscribe(resp => {
      this._informeEncuestaSatisfaccionGrupo = resp
     });
  }

  get informeEncuestaSatisfaccionGrupo() {
    return this._informeEncuestaSatisfaccionGrupo;
  }
}


