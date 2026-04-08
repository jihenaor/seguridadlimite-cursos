import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Nivel } from '../models/nivel.model';
import { environment } from '../../../environments/environment';


@Injectable({
  providedIn: 'root'
})
export class NivelesUpdateDateService {


  constructor(private http: HttpClient) {}

  updateFechas(niveles: Nivel[]): Observable<any> {

    return this.http.post(`${environment.apiUrl}/nivel/update-dates`, niveles);
  }
}
