import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { DomSanitizer } from '@angular/platform-browser';

import { Documento } from './../../../core/models/documento.model';
import { Fototrabajador } from './../../../core/models/fototrabajador.model';

import { Aprendiz } from 'src/app/core/models/aprendiz.model';

import { ServicesService } from './../../../core/service/services.service';
import { DatevalidatorService } from './../../../utils/datevalidator.service';
import { MatTabChangeEvent, MatTabsModule } from '@angular/material/tabs';
import { AprendizDocumentosService } from './aprendiz-documentos.service';

import { ConsultaAsistenciaComponent } from '../components/consulta-asistencia/consulta-asistencia.component';
import { PhotoComponent } from '../components/photo/photo.component';
import { FirmaComponent } from '../components/firma/firma.component';
import { DocumentosAprendizComponent } from '../components/documentos-aprendiz/documentos-aprendiz.component';
import { InformacionEvaluacionComponent } from '../components/informacion-evaluacion/informacion-evaluacion.component';
import { InformacionAcademicaComponent } from '../components/informacion-academica/informacion-academica.component';
import { DocumentosTrabajadorComponent } from '../components/documentos-trabajador/documentos-trabajador.component';
import { FormEditTrabajadorComponent } from '../components/form-edit-trabajador/form-edit-trabajador.component';
import { PagetitleComponent } from '../../../shared/components/page-title/pagetitle.component';


@Component({
    selector: 'app-about-student',
    templateUrl: './about-student.component.html',
    styleUrls: ['./about-student.component.scss'],
    imports: [PagetitleComponent, MatTabsModule, FormEditTrabajadorComponent, DocumentosTrabajadorComponent, InformacionAcademicaComponent, InformacionEvaluacionComponent, DocumentosAprendizComponent, FirmaComponent, PhotoComponent, ConsultaAsistenciaComponent]
})
export class AboutStudentComponent implements OnInit {
  public aprendiz: Aprendiz;
  public idtrabajador: string | null;
  public idgrupo: string | null;
  public idaprendiz: string | null;
  public selectedTab: number = 0;
  public panelOpenState = false;
  public fototrabajador: Fototrabajador;
  public documento: Documento;

  breadscrums = [
    {
      title: 'Actualizar aprendiz',
      items: [],
      active: 'All Student',
    },
  ];

  constructor(
    private route: ActivatedRoute,
    private sanitizer: DomSanitizer,
    private datevalidatorService: DatevalidatorService,
    private aprendizDocumentosService: AprendizDocumentosService
  ) {
    // Obtener parámetros de ruta o de sesión
    this.route.params.subscribe((params) => {
      if (Object.keys(params).length === 0) {
        this.idtrabajador = sessionStorage.getItem('idtrabajador');
        this.idaprendiz = sessionStorage.getItem('idaprendiz');
      } else {
        this.idtrabajador = params['idtrabajador'];
        this.idgrupo = params['idgrupo'];
        this.idaprendiz = params['idaprendiz'];
      }
    });
  }

  ngOnInit(): void {
    if (this.idaprendiz) {
      // Suscribirse al observable aprendiz$
      this.aprendizDocumentosService.aprendiz$.subscribe((data) => {
        if (data) {
          this.aprendiz = data;
          this.idtrabajador = this.aprendiz.trabajador.id.toString();
        }
      });

      // Obtener datos del aprendiz
      this.aprendizDocumentosService.getAprendiz(Number(this.idaprendiz));
    }
  }

  transform(base64) {
    return this.sanitizer.bypassSecurityTrustResourceUrl('data:image/' + this.aprendiz.trabajador.ext + ';base64,' + base64);
  }

  onUploadFotoChange(evt: any) {
    const file = evt.target.files[0];

    if (file) {
      const reader = new FileReader();

      if (this.fototrabajador === undefined || this.fototrabajador == null) {
        this.fototrabajador = new Fototrabajador({
          id: this.idtrabajador,
        });
      }

      reader.onload = this.handleFotoReaderLoaded.bind(this);
      reader.readAsBinaryString(file);
    }
  }

  handleFotoReaderLoaded(e) {
    this.fototrabajador.base64 = btoa(e.target.result);
  }

  onUploadChangeAprendiz(evt: any, documento: Documento) {
    const file = evt.target.files[0];

    if (file) {
      const reader = new FileReader();

      this.documento = documento;
      this.documento.ext = file.name.split('.').pop();
      this.documento.nombrefile = file.name;

      reader.onload = this.handleAprendizReaderLoaded.bind(this);
      reader.readAsBinaryString(file);
    }
  }

  handleAprendizReaderLoaded(e) {
    this.documento.base64 = btoa(e.target.result);
  }

  isFechaMayorFechaActual(fecha: string): boolean {
    return this.datevalidatorService.isFechaMayorFechaActual(fecha);
  }

  onTabChange(event: MatTabChangeEvent) {
    this.selectedTab = event.index;


  }
}
