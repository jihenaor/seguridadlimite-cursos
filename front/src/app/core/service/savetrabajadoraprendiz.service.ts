import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Observable, map } from 'rxjs';
import { environment } from '../../../environments/environment';


@Injectable({
  providedIn: 'root'
})

export class SaveTrabajadorAprendizService {
  constructor(private http: HttpClient) {}

    save(datos: any): Observable<any> {

    const url = `${environment.apiUrl}/aprendiz/save`;
    return this.http.post<any>(url, datos).pipe(
        map(response => {

        return response;
      })
    );
  }
}
