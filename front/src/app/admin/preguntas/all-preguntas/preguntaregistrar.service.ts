import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';

import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
@Injectable({
  providedIn: 'root',
})
export class PreguntasRegistrarService {

  constructor(private httpClient: HttpClient) {}

  savePregunta(pregunta: any): Observable<any> {
    const url = `${environment.apiUrl}/pregunta`;
    return this.httpClient.post<any>(url, pregunta).pipe(
      map(response => {

        return response;
      })
    );
  }


}
