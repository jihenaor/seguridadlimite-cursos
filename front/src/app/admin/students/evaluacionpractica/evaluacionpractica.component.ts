import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { ActivatedRoute, RouterLink } from '@angular/router';
import { Router } from "@angular/router";
import { EvaluacionpracticaService } from './evaluacionpractica.service';
import { ServicesService } from '../../../core/service/services.service';
import { Evaluacion } from '../../../core/models/evaluacion.model';
import { Grupoevaluacion } from './../../../core/models/grupoevaluacion.model';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatTableModule } from '@angular/material/table';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { NgIf, NgFor } from '@angular/common';

@Component({
    selector: 'app-evaluacionpractica',
    templateUrl: './evaluacionpractica.component.html',
    styleUrls: ['./evaluacionpractica.component.sass'],
    imports: [
        RouterLink,
        NgIf,
        NgFor,
        MatButtonModule,
        MatIconModule,
        MatTableModule,
    ]
})
export class EvaluacionpracticaComponent implements OnInit {
  id: number;
  students: Grupoevaluacion | null;
  idaprendiz: number;
  idgrupo: number;
  grupoevaluacions: Grupoevaluacion[];
  displayedColumns = ['pregunta', 'cumple', 'nocumple'];
  dataSource = [];
  evaluacions = [];
  grupo: Grupoevaluacion;
  nombrecompleto: string;
  documentoevaluacion = {
                          tipo: 'P',
                          ext: '',
                          nombrearchivo: '',
                          base64: '',
                          idaprendiz: 0
                        };

  @ViewChild("fileUpload", {static: false}) fileUpload: ElementRef;

  constructor(
    public httpClient: HttpClient,
    public dialog: MatDialog,
    public asistenciaService: EvaluacionpracticaService,
    private services: ServicesService,
    private snackBar: MatSnackBar,
    private route: ActivatedRoute,
    private router: Router
  ) {
    this.route.params.subscribe(params => {
      if (params['idaprendiz'] !== undefined) {
        this.idaprendiz = params['idaprendiz'];
        this.documentoevaluacion.idaprendiz = this.idaprendiz;
      }
      if (params['idgrupo'] !== undefined) {
        this.idgrupo = params['idgrupo'];
      }
   });
  }
  async ngOnInit() {
    await this.loadGrupoevaluacion();

    this.nombrecompleto = sessionStorage.getItem('aprendiz_nombrecompleto');
  }

  async loadGrupoevaluacion() {
    this.grupoevaluacions = await this.services.executeFetch('/evaluacion/' + this.idaprendiz + '/practica');
  }

  onClickDocumento() {
    const fileUpload = this.fileUpload.nativeElement;

    fileUpload.click();
  }

  onUploadChangeA(evt: any) {
    const file = evt.target.files[0];

    if (file) {
      const reader = new FileReader();

      this.documentoevaluacion.ext = file.name.split('.').pop();
      this.documentoevaluacion.nombrearchivo = file.name;
      reader.onload = this.handleReaderLoaded.bind(this);
      reader.readAsBinaryString(file);
    }
  }

  handleReaderLoaded(e) {
    this.documentoevaluacion.base64 = btoa(e.target.result);
  }

  async updateDocumentoEvaluacion() {
    try {
      if (this.documentoevaluacion.base64 !== undefined) {
        const ra = await this.services.post('/saveDocumentoevaluacion', this.documentoevaluacion);
        alert('Actualización exitosa');
      }
    } catch (error) {
      alert(error);
    }
  }

  seleccionarGrupo(grupo: Grupoevaluacion) {
    this.grupo = grupo;
    this.evaluacions = grupo.evaluacions;
    this.dataSource = grupo.evaluacions;
  }

  seleccionarRespuesta(evaluacion: Evaluacion, numero: number) {
    evaluacion.numerorespuesta = numero;

    if (numero === evaluacion.pregunta.numerorespuestacorrecta) {
      evaluacion.respuestacorrecta = 'S';
    } else {
      evaluacion.respuestacorrecta = 'N';
    }
  }

  evaluar(evaluacion: Evaluacion, cumple: string) {
    evaluacion.respuestacorrecta = cumple;
  }

  async actualizarEvaluacion() {
    const r = await this.services.post('saveEvaluacionpractica', this.grupoevaluacions);

    if (r.code === '200') {
      alert('Actualización exitosa');
    } else {
      alert('Code: ' + r.code + '.  Error ' + r.msg);
    }
  }

  showNotification(colorName, text, placementFrom, placementAlign) {
    this.snackBar.open(text, '', {
      duration: 2000,
      verticalPosition: placementFrom,
      horizontalPosition: placementAlign,
      panelClass: colorName,
    });
  }

  async downloadEvaluacionPractica() {
    const r = await this.services.executeFetch('/formatoevaluacionpractica/' + this.idaprendiz);

    const linkSource = 'data:application/pdf;base64,' + r.base64 + '\n';
    const downloadLink = document.createElement("a");
    const fileName = "evaluacionpractica.pdf";

    downloadLink.href = linkSource;
    downloadLink.download = fileName;
    downloadLink.click();
  }
}

