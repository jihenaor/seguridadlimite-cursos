import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { environment } from './../../../environments/environment';
import { Observable, map } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class UpdateParametrosEvaluationDateService {
  constructor(private http: HttpClient) {}

  updateEvaluacionDate(): Observable<any> {
    const url = `${environment.apiUrl}/parametros/updateEvaluationDate`;
    return this.http.post<any>(url, {}).pipe(
      map(response => {

        return response;
      })
    );
  }

  updateEncuestaDate(): Observable<any> {
    const url = `${environment.apiUrl}/parametros/updateEncuestaDate`;
    return this.http.post<any>(url, {}).pipe(
      map(response => {

        return response;
      })
    );
  }
}
