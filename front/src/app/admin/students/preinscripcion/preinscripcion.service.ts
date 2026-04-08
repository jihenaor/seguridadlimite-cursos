import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';

import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { environment } from 'src/environments/environment';
@Injectable({
  providedIn: 'root',
})
export class PreinscripcionService {

  constructor(private httpClient: HttpClient) {}

  save(aprendiz: any): Observable<any> {
    const url = `${environment.apiUrl}/aprendiz/save`;
    return this.httpClient.post<any>(url, aprendiz).pipe(
      map(response => {

        return response;
      })
    );
  }
}
