import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class EncuestaService {
  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) { }

  generateEncuestaPdf(idaprendiz: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/encuesta/pdf/${idaprendiz}`, {
      responseType: 'blob' as 'json'
    });
  }
}
