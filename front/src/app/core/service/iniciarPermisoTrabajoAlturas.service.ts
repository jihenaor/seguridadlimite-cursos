import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Nivel } from '../models/nivel.model';
import { environment } from '../../../environments/environment';


@Injectable({
  providedIn: 'root'
})
export class IniciarPermisoTrabajoAlturasService {

  constructor(private http: HttpClient) {}

  startWorkPermissions(niveles: Nivel[]): Observable<any> {

    return this.http.post(`${environment.apiUrl}/permisos/start-work-permissions`, niveles);
  }
}
