import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Documento } from '../models/documento.model';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class DocumentoService {
  private readonly MAX_FILE_SIZE_MB = 3; // Tamaño máximo en MB
  private readonly MAX_FILE_SIZE_BYTES = this.MAX_FILE_SIZE_MB * 1024 * 1024;

  constructor(private http: HttpClient) {}

  guardarDocumentos(documentos: Documento[]): Observable<any> {
    const validDocuments = documentos.filter((doc) => this.validateDocumentSize(doc));

    if (validDocuments.length === 0) {
      return throwError(() => new Error(`No hay documentos válidos para guardar. El tamaño máximo permitido es ${this.MAX_FILE_SIZE_MB} MB.`));
    }

    return this.http.post(`${environment.apiUrl}/saveDocumentoaprendiz`, validDocuments).pipe(
      catchError((error) => {
        console.error('Error al guardar documentos:', error);
        return throwError(() => new Error('Ocurrió un error al guardar los documentos.'));
      })
    );
  }

  private validateDocumentSize(documento: Documento): boolean {
    if (!documento.base64) return false;

    const decodedSize = this.calculateBase64Size(documento.base64);
    return decodedSize <= this.MAX_FILE_SIZE_BYTES;
  }


  private calculateBase64Size(base64: string): number {
    const padding = (base64.match(/=/g) || []).length;
    const base64Length = base64.length - padding;
    return (base64Length * 3) / 4; // Fórmula para convertir Base64 a bytes
  }
}
