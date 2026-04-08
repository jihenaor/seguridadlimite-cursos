import { Component, OnInit, Input } from '@angular/core';

import { FileUploadBase64Component } from '../../../../shared/components/file-upload-base64/file-upload-base64.component';
import { Filebase64Service } from './../../../../core/service/filebase64.service';
import { MatButtonModule } from '@angular/material/button';
import { NgFor, NgIf } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { DocumentoService } from '../../../../core/service/Documento.service';
import { Documento } from '../../../../core/models/documento.model';
import { Aprendiz } from './../../../../core/models/aprendiz.model';
import { MatIconModule } from '@angular/material/icon';

@Component({
    selector: 'aprendiz-documentos-aprendiz',
    templateUrl: './documentos-aprendiz.component.html',
    imports: [FormsModule, NgFor, MatButtonModule, NgIf, FileUploadBase64Component]
})
export class DocumentosAprendizComponent implements OnInit {
  @Input() aprendiz: Aprendiz;

  constructor(
    private documentoService: DocumentoService,
    private filebase64Service: Filebase64Service
  ) {}

  ngOnInit(): void {}

  /**
   * Guarda los documentos del aprendiz.
   */
  guardarDocumentos(documentos: Documento[]): void {
    this.documentoService.guardarDocumentos(documentos).subscribe({
      next: () => {
        alert('Documentos guardados con éxito');
      },
      error: (err) => {
        alert(`Error al guardar documentos: ${err.message || 'Error desconocido'}`);
      },
    });
  }

  /**
   * Descarga un documento en formato PDF.
   */
  showPdf(base64: string): void {
    const linkSource = `data:application/pdf;base64,${base64}`;
    const downloadLink = document.createElement('a');
    downloadLink.href = linkSource;
    downloadLink.download = 'documento.pdf';
    downloadLink.click();
  }

  /**
   * Descarga un documento diferente a PDF.
   */
  showDocument(documento: Documento): void {
    const linkSource = `data:application/${documento.ext};base64,${documento.base64}`;
    const downloadLink = document.createElement('a');
    downloadLink.href = linkSource;
    downloadLink.download = `documento.${documento.ext}`;
    downloadLink.click();
  }

  /**
   * Maneja el cambio de archivo cargado en base64.
   */
  onBase64Change(base64: string | null, documento: Documento): void {
    if (base64) {
      const fileData = this.filebase64Service.separateBase64Data(base64);
      documento.base64 = fileData.content;
      documento.ext = fileData.extension;
    }
  }
}
