import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../../../environments/environment';

export interface ImportResultado {
  creados: number;
  omitidos: number;
  errores: number;
  error?: string;
}

@Injectable({
  providedIn: 'root'
})
export class AprendizExcelService {
  constructor(private httpClient: HttpClient) {}

  exportToExcel(): Observable<Blob> {
    const url = `${environment.apiUrl}/aprendices/export/excel`;
    return this.httpClient.get(url, {
      responseType: 'blob',
      headers: new HttpHeaders().append('Accept', 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet')
    });
  }

  importarDesdeExcel(file: File): Observable<ImportResultado> {
    const url = `${environment.apiUrl}/upload`;
    const formData = new FormData();
    formData.append('file', file, file.name);
    return this.httpClient.post<ImportResultado>(url, formData);
  }
}
