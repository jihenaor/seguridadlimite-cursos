import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { HttpClient, HttpErrorResponse, HttpHeaders, HttpParams } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { InformeMinisterio } from '../models/informe-ministerio.model';

@Injectable()
export class GruposministerioService {
  dataChange: BehaviorSubject<InformeMinisterio[]> = new BehaviorSubject<InformeMinisterio[]>([]);
  dialogData: any;
  filterData: any;
  msgError: string;
  constructor(private httpClient: HttpClient) {}
  get data(): InformeMinisterio[] {
    return this.dataChange.value;
  }
  getDialogData() {
    return this.dialogData;
  }

  getFilterData() {
    if (!this.filterData) {
      this.filterData = {
        aucodestad: 'A',
        idprograma: undefined
      };
    }
    return this.filterData;
  }


  /** CRUD METHODS */
  consultarDatosMinisterio(idPermisosTrabajos: number[]): Observable<InformeMinisterio[]> {
    const url = `${environment.apiUrl}/findministerio/consultar`;

    // Create query parameters for the GET request
    let params = new HttpParams();
    idPermisosTrabajos.forEach(id => {
      params = params.append('idPermisosTrabajos', id.toString());
    });

    return this.httpClient.get<InformeMinisterio[]>(url, { params });
  }

  getGruposMinisterio(idPermisosTrabajos: number[]): void {
    this.consultarDatosMinisterio(idPermisosTrabajos).subscribe(
      data => {
        this.dataChange.next(data);
      },
      (error: HttpErrorResponse) => {
        this.msgError = error.name + ' ' + error.message;
        console.error('Error consultando datos ministerio:', this.msgError);
      }
    );
  }
  // Additional utility methods
  setFilterData(filter: any) {
    this.filterData = filter;
  }
}
