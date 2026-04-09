import { Injectable } from '@angular/core';
import { Empresa } from '../../../core/models/empresa.model';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { environment } from 'src/environments/environment';

@Injectable()
export class EmpresasService {

  private _empresas: Empresa[] | null;
  private _empresasFiltered: Empresa[] | null;

  dialogData: any;
  constructor(private httpClient: HttpClient) {}

  getDialogData() {
    return this.dialogData;
  }

  getAllEmpresas(): void {
    this.httpClient.get<Empresa[]>(`${environment.apiUrl}` + '/empresas').subscribe(
      data => {
        this._empresas = data;
        this._empresasFiltered = data;
      },
    );
  }

  get empresas() {
    return this._empresasFiltered;
  }

  filter(filter: string) {
    if (!filter || filter.length === 0) {
      this._empresasFiltered = this._empresas;
    } else {
      this._empresasFiltered = this._empresasFiltered = this.empresas
      .filter((empresa: Empresa) => {
        const searchStr = (empresa.nombre || '').toLowerCase();
        const nitStr = (empresa.numerodocumento || '').toLowerCase();
        const searchTerm = filter.toLowerCase();
        return searchStr.indexOf(searchTerm) !== -1 || nitStr.indexOf(searchTerm) !== -1;
      });
    }
  }

  async addEmpresa(empresa: Empresa) {
    const url = `${environment.apiUrl}` + '/saveEmpresa';

    const datos = JSON.stringify(empresa);
    const token = sessionStorage.getItem('token');
    try {
      const response = await fetch(url,
        {
          method: 'POST',
          body: datos,
          headers: {
            'Content-Type': 'application/json',
            ...(token ? { 'Authorization': `Bearer ${token}` } : {}),
          }
        });
      if (!response.ok) {
        alert('Se genero un error ' + response.statusText);
      } else {
        const data = await response.json();

        this.dialogData = empresa;

        return data;
      }
    } catch (error) {
      alert(error);
    }
  }
}
