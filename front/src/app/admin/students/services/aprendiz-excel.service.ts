import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../../../environments/environment';

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
}
