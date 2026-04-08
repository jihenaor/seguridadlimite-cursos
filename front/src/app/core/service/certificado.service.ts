import { environment } from './../../../environments/environment';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class CertificadoService {
  constructor(private http: HttpClient) {}

  public uploadCertificado(file: File, idaprendiz: number): Observable<any> {
    const formData = new FormData();
    formData.append('file', file);
    formData.append('idaprendiz', idaprendiz.toString());

    const headers = new HttpHeaders();

    return this.http.post(
      `${environment.apiUrl}/certificado/upload`,
      formData,
      { headers }
    );
  }
}
