import { Injectable, inject, signal, computed } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { environment } from 'src/environments/environment';
import { Trabajador } from '../models/trabajador.model';

interface State {
  trabajadors: Trabajador[] | null;
  loading: boolean;
}

@Injectable({
  providedIn: 'root',
})
export class EmpresaFindCertificateService {

  private http = inject(HttpClient);

  #state = signal<State>({
    trabajadors: [],
    loading: true,
  })

  public trabajadors = computed(() => this.#state().trabajadors);

  public searchTrabajadorCertificado(
    filtro: string,
    fechaInicial: string,
    fechaFinal: string
  ): void {
    this.http.get<Trabajador[]>(`${environment.apiUrl}/empresafindcertificate/${filtro}/${fechaInicial}/${fechaFinal}`)
      .subscribe(resp =>{
        console.log(resp);
        this.#state.set({
          loading: false,
          trabajadors: resp
        })
    });
  }
}
