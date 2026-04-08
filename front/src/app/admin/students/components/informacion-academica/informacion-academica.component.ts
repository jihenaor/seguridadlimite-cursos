import { Component, Input, OnInit } from '@angular/core';

import { Aprendiz } from 'src/app/core/models/aprendiz.model';
import { Arl } from 'src/app/core/models/arl.model';
import { Empresa } from 'src/app/core/models/empresa.model';
import { Eps } from 'src/app/core/models/eps.model';
import { Grupo } from 'src/app/core/models/grupo.model';

import { DatevalidatorService } from 'src/app/utils/datevalidator.service';
import { ServicesService } from 'src/app/core/service/services.service';
import { CertificadoService } from 'src/app/core/service/certificado.service';
import { Observable, of } from 'rxjs';
import { PermisotrabajoalturasService } from '../../../../core/service/permisotrabajoalturas.service';
import { ShowNotificacionService } from 'src/app/core/service/show-notificacion.service';
import { UpdateAprendizService } from '../../../../core/service/updateaprendiz.service';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatInputModule } from '@angular/material/input';
import { MatOptionModule } from '@angular/material/core';
import { FormsModule } from '@angular/forms';
import { MatSelectModule } from '@angular/material/select';
import { MatFormFieldModule } from '@angular/material/form-field';
import { FormatoEvaluacionComponent } from '../formato-evaluacion/formato-evaluacion.component';
import { InformePerfilIngresoComponent } from '../informe-perfil-ingreso/informe-perfil-ingreso.component';
import { InformeFormatoInscripcionComponent } from '../informe-formato-inscripcion/informe-formato-inscripcion.component';
import { MatIconModule } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu';
import { MatButtonModule } from '@angular/material/button';
import { RouterLink } from '@angular/router';
import { NgIf, NgFor, AsyncPipe } from '@angular/common';
import { MatExpansionModule } from '@angular/material/expansion';
import { DescargarCertificadoService } from '../../../../core/service/descargarcertificado.service';
import { Departamento } from '@model/departamento.model';
import { Ciudad } from '@model/ciudad.model';
import { DepartamentService } from 'src/app/core/service/departamentos.service';
import { NivelsService } from '../../../../core/service/nivels.service';
import { Nivel } from '@model/nivel.model';
import { TipoTrabajo, TipoTrabajoService } from '../../../../core/service/tipo-trabajo.service';
import { EncuestaService } from 'src/app/core/service/encuesta.service';
import { MatCardModule } from '@angular/material/card';

@Component({
    selector: 'aprendiz-informacion-academica',
    templateUrl: './informacion-academica.component.html',
    imports: [
        MatExpansionModule,
        NgIf,
        RouterLink, MatButtonModule, MatMenuModule, MatIconModule,
        InformeFormatoInscripcionComponent,
        InformePerfilIngresoComponent,
        FormatoEvaluacionComponent,
        MatFormFieldModule,
        MatSelectModule,
        MatCardModule,
        FormsModule,
        NgFor,
        MatOptionModule,
        MatInputModule,
        MatAutocompleteModule    ]
})
export class InformacionAcademicaComponent implements OnInit {
  @Input()
  aprendiz: Aprendiz

  public epss: Eps[] = [];
  public arls: Arl[] = [];
  public empresas: Empresa[] = [];
  public gruposActivos: Grupo[] = [];
  public cambiandoGrupo = false;
  public filteredEmpresas: Observable<Empresa[]>;
  public departamentos: Departamento[] = [];
  public ciudadesDomicilio: Ciudad[] = [];
  public nivels: Nivel[];
  public loading = false;
  public loadingEncuesta = false;

  constructor(
    private datevalidatorService: DatevalidatorService,
    public service: ServicesService,
    private certificadoService: CertificadoService,
    private descargarCertificadoService: DescargarCertificadoService,
    private permisotrabajoalturasService: PermisotrabajoalturasService,
    private notificacionService: ShowNotificacionService,
    private updateAprendizService: UpdateAprendizService,
    private departamentosService: DepartamentService,
    private nivelesService: NivelsService,
    private tipoTrabajoService: TipoTrabajoService,
    private encuestaService: EncuestaService
    ) { }

  async ngOnInit() {
    this.departamentos = this.departamentosService.getDepartamentos();
    this.epss = await this.service.executeFetch('/epss');
    this.arls = await this.service.executeFetch('/arls');
    this.empresas = await this.service.executeFetch('/empresas');

    if (this.aprendiz) {
      this.ciudadesDomicilio = this.departamentosService.getCiudades(this.aprendiz.departamentodomicilio);
    }

    this.loadNiveles();
  }

  onEmpresaChange(newValue: string) {
    const filterValue = newValue.toLowerCase();
    this.filteredEmpresas = of(this.empresas.filter(empresa => empresa.nombre.toLowerCase().includes(filterValue)));
  }

  isFechaMayorFechaActual(fecha: string): boolean {
    return this.datevalidatorService.isFechaMayorFechaActual(fecha);
  }

  private downloadPDF(reporte: any, fileName: string) {
    const linkSource = 'data:application/pdf;base64,' + reporte.base64 + '\n';
    const downloadLink = document.createElement('a');

    downloadLink.href = linkSource;
    downloadLink.download = fileName;
    downloadLink.click();
  }

  subirCertificadoPDF(event: any) {
    const file = event.target.files[0];

    if (file) {
      if (file.type !== 'application/pdf') {
        this.notificacionService.displayWarning('Solo se permiten archivos PDF');
        event.target.value = '';
        return;
      }

      const maxSize = 5 * 1024 * 1024; // 5MB en bytes
      if (file.size > maxSize) {
        this.notificacionService.displayWarning('El archivo no debe superar los 5MB');
        event.target.value = '';
        return;
      }

      this.certificadoService.uploadCertificado(file, this.aprendiz.id).subscribe({
        next: () => {
          this.notificacionService.displaySuccess('Certificado subido exitosamente');
          event.target.value = '';
        },
        error: (error) => {
          console.error('Error al subir el certificado:', error);
          this.notificacionService.displayError('Error al subir el certificado');
          event.target.value = '';
        }
      });
    } else {
      this.notificacionService.displayWarning('No se seleccionó ningún archivo');
    }
  }

  descargarCertificadoPDF() {
    this.loading = true;

    this.descargarCertificadoService.downloadCertificado(this.aprendiz.id).subscribe({
      next: (reporte) => {
        this.downloadPDF(reporte, 'certificado.pdf');
        this.notificacionService.displaySuccess('Certificado descargado exitosamente');

        this.loading = false;
      },
      error: (error) => {
        this.loading = false;

        if (error === 'No existe código de verificación' || error === 'No se encontro el certificado del aprendiz') {
          this.notificacionService.displayWarning(error);
        } else {
          this.notificacionService.displayError('Error al descargar el certificado');
        }
      },
    });
  }

  async updateAprendiz() {
    this.updateAprendizService.updatePerfil(this.aprendiz)
    .subscribe(() => {
        this.notificacionService.displaySuccess('Actualización exitosa');
      }
    );
  }

  onDepartamentoChange() {
    this.ciudadesDomicilio = this.departamentosService.getCiudades(this.aprendiz.departamentodomicilio);
  }

  async showPdf(tipo: string) {
    this.loading = true;

    try {
      const r = await this.service.executeFetch('/' + this.aprendiz.id + '/' + tipo);
      this.loading = false;
      const linkSource = 'data:application/pdf;base64,' + r.base64 + '\n';
      const downloadLink = document.createElement("a");
      const fileName = tipo + ".pdf";

      downloadLink.href = linkSource;
      downloadLink.download = fileName;
      downloadLink.click();
    } catch (error) {
      this.loading = false;
    }
  }

  downloadEncuesta() {
    this.loadingEncuesta = true;

    this.encuestaService.generateEncuestaPdf(this.aprendiz.id).subscribe({
      next: (blob: Blob) => {
        // Crear URL del blob
        const url = window.URL.createObjectURL(blob);

        // Crear elemento anchor temporal
        const link = document.createElement('a');
        link.href = url;
        link.download = 'encuesta.pdf';

        // Añadir al DOM y simular click
        document.body.appendChild(link);
        link.click();

        // Limpiar
        document.body.removeChild(link);
        window.URL.revokeObjectURL(url);

        this.notificacionService.displaySuccess('Encuesta descargada exitosamente');
        this.loadingEncuesta = false;
      },
      error: (error) => {
        this.loadingEncuesta = false;
        console.error('Error:', error);
        this.notificacionService.displayError('Error al descargar la encuesta');
      }
    });
  }

  loadNiveles() {
    this.nivelesService.getNivelActivos().subscribe({
      next: (niveles) => {
        this.nivels = niveles;
      },
      error: (err) => {
        console.error('Error al cargar niveles:', err);
      }
    });
  }
}
