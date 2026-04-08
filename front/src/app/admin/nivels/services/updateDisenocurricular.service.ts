import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Observable, catchError, map, of, tap, throwError } from 'rxjs';
import { Disenocurricular } from './../../../core/models/disenocurricular.model';

import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class UpdateDisenocurricularService {
  constructor(private http: HttpClient) {}

    update(disenocurricular: Disenocurricular): Observable<any> {
    const url = `${environment.apiUrl}/disenocurricular`;
    return this.http.post<any>(url, disenocurricular).pipe(
      map(response => {

        return response;
      })
    );
  }
}
