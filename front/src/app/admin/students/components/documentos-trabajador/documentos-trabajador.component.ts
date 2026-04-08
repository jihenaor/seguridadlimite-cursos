import { Component, Input, OnInit } from '@angular/core';

import { Trabajador } from 'src/app/core/models/trabajador.model';
import { Trabajadordocumento } from 'src/app/core/models/trabajadordocumento.model';

import { ServicesService } from 'src/app/core/service/services.service';
import { Filebase64Service } from './../../../../core/service/filebase64.service';
import { Aprendiz } from 'src/app/core/models/aprendiz.model';
import { FileUploadBase64Component } from '../../../../shared/components/file-upload-base64/file-upload-base64.component';
import { MatButtonModule } from '@angular/material/button';
import { NgIf } from '@angular/common';

@Component({
    selector: 'app-documentos-trabajador',
    templateUrl: './documentos-trabajador.component.html',
    imports: [NgIf, MatButtonModule, FileUploadBase64Component]
})
export class DocumentosTrabajadorComponent implements OnInit {
  @Input()
  aprendiz: Aprendiz;
  public trabajador: Trabajador;

  ladoa: string;
  ladob: string;

  public trabajadordocumento: Trabajadordocumento;

  constructor(
    public service: ServicesService,
    private filebase64Service: Filebase64Service) { }

  ngOnInit() {
    this.trabajador = this.aprendiz.trabajador;

    if (this.trabajador) {
      this.trabajadordocumento = new Trabajadordocumento({
        id: this.trabajador.id,
        adjuntodocumento: this.trabajador.adjuntodocumento,
        ext: this.trabajador.ext
      });
    }
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
        const ra = await this.service.post('/saveDocumentotrabajador', this.trabajadordocumento);

        alert('Actualización exitosa');
    } catch (error) {
      alert(error);
    }
  }

  showPdf(base64: string) {
    if (!base64 || base64.length < 10) {
      alert("El archivo no tiene un formato válido")
      return;
    }
    const linkSource = 'data:application/pdf;base64,' + ' ' + base64;
    const downloadLink = document.createElement("a");
    const fileName = "doc.pdf";

    downloadLink.href = linkSource;
    downloadLink.download = fileName;
    downloadLink.click();
  }

  inicializarDocumentos() {
    this.trabajador.adjuntodocumento = 'N';
  }

  onBase64Ladoa(value: string | null) {
    this.ladoa = value;
  }

  onBase64Ladob(value: string | null) {
    this.ladob = value;
  }
}
