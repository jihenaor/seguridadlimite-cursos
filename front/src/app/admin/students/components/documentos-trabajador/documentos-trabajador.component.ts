import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';

import { Trabajador } from 'src/app/core/models/trabajador.model';
import { Trabajadordocumento } from 'src/app/core/models/trabajadordocumento.model';

import { ServicesService } from 'src/app/core/service/services.service';
import { Filebase64Service } from './../../../../core/service/filebase64.service';
import { Aprendiz } from 'src/app/core/models/aprendiz.model';
import { AprendizDocumentosService } from '../../about-student/aprendiz-documentos.service';
import { FileUploadBase64Component } from '../../../../shared/components/file-upload-base64/file-upload-base64.component';
import { MatButtonModule } from '@angular/material/button';
import { NgIf } from '@angular/common';

@Component({
    selector: 'app-documentos-trabajador',
    templateUrl: './documentos-trabajador.component.html',
    imports: [NgIf, MatButtonModule, FileUploadBase64Component]
})
export class DocumentosTrabajadorComponent implements OnInit, OnChanges {
  @Input()
  aprendiz: Aprendiz;
  public trabajador: Trabajador;

  ladoa: string;
  ladob: string;

  public trabajadordocumento: Trabajadordocumento;

  constructor(
    public service: ServicesService,
    private filebase64Service: Filebase64Service,
    private aprendizDocumentosService: AprendizDocumentosService
  ) {}

  ngOnInit() {
    this.syncFromAprendiz();
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['aprendiz']) {
      this.syncFromAprendiz();
    }
  }

  private syncFromAprendiz(): void {
    if (!this.aprendiz?.trabajador) {
      return;
    }
    this.trabajador = this.aprendiz.trabajador;
    this.trabajadordocumento = new Trabajadordocumento({
      id: this.trabajador.id,
      adjuntodocumento: this.trabajador.adjuntodocumento,
      ext: this.trabajador.ext
    });
  }

  async updateDocumentoTrabajador() {
    if (!this.ladoa || this.ladoa.length === 0) {
      alert('Debe seleccionar un documento para el lado A')
      return;
    } else {
      const datosa = this.filebase64Service.separateBase64Data(this.ladoa);
      this.trabajadordocumento.base64a = datosa.content;
      this.trabajadordocumento.ext = datosa.extension;
    }

    if (this.ladob && this.ladob.length >= 0) {
      const datosb = this.filebase64Service.separateBase64Data(this.ladob);
      this.trabajadordocumento.base64b = datosb.content;
    }

    if (this.trabajadordocumento.base64a === this.trabajadordocumento.base64b) {
      alert('Los documentos seleccionados para el lado a A y para el labo B son iguales');
      return;
    }
    try {
      await this.service.post('/saveDocumentotrabajador', this.trabajadordocumento);
      this.aprendizDocumentosService.getAprendiz(this.aprendiz.id);
      this.ladoa = '';
      this.ladob = '';
      alert('Documento guardado. La ficha se ha actualizado con los archivos registrados.');
    } catch (error) {
      alert(error);
    }
  }

  showPdf(base64: string) {
    if (!base64 || base64.length < 10) {
      alert("El archivo no tiene un formato válido")
      return;
    }
    const linkSource = 'data:application/pdf;base64,' + base64.trim();
    const downloadLink = document.createElement("a");
    const fileName = "doc.pdf";

    downloadLink.href = linkSource;
    downloadLink.download = fileName;
    downloadLink.click();
  }

  /**
   * Permite elegir archivos nuevos. No borra nada en servidor hasta que pulse «Guardar documento»;
   * en ese momento {@code saveDocumentotrabajador} vuelve a escribir las mismas rutas y sustituye el documento anterior.
   */
  inicializarDocumentos(): void {
    this.trabajador.adjuntodocumento = 'N';
  }

  onBase64Ladoa(value: string | null) {
    this.ladoa = value;
  }

  onBase64Ladob(value: string | null) {
    this.ladob = value;
  }
}
