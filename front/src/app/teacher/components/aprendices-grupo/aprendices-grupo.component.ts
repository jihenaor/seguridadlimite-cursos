import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatDialogModule, MatDialog } from '@angular/material/dialog';
import { CommonModule } from '@angular/common';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';

import { Aprendiz } from 'src/app/core/models/aprendiz.model';
import { ComunicacionService } from '../../services/comunicacion.service';
import { AprendizGrupoTeacherService } from '../../services/aprendizgrupoteacher.service';
import { Grupo } from 'src/app/core/models/grupo.model';
import { Asistencia } from '../../../core/models/asistencia.model';
import { RegistrarasistenciaService } from '../../services/registrarasistencia.service';

import { ParametrosFindService } from '../../../core/service/parametrosFind.service';
import { MediaService } from '../../../core/service/media.service';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { NgFor, NgIf } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';

import { FotoModalComponent } from '../foto-modal/foto-modal.component'; // Ruta del componente del modal
import { CapturarFirmaComponent } from '../capturar-firma/capturar-firma.component';

@Component({
    selector: 'teacher-aprendices-grupo',
    styleUrls: ['./aprendices-grupo.component.scss'],
    templateUrl: './aprendices-grupo.component.html',
    imports: [
        MatButtonModule,
        MatIconModule,
        NgFor,
        MatSlideToggleModule,
        NgIf,
        FormsModule,
        MatDialogModule,
        CommonModule,
        MatTableModule,
        MatPaginatorModule,
        MatFormFieldModule,
        MatInputModule
    ]
})
export class AprendicesGrupoComponent implements OnInit {
  public grupo: Grupo;
  public aprendizs: Aprendiz[] = [];
  public apredizSeleccionado: number = -1;
  public modulos: string[] = [];
  public mostrarEncuesta: boolean = false;
  public mostrarFoto: boolean = false;
  public mostrarFirma: boolean = false;
  public fechaSeleccionada: string = '';

  @Output()
  aprendizSelected: EventEmitter<Aprendiz> = new EventEmitter<Aprendiz>();

  breadscrums = [
    {
      title: 'Basic',
      items: ['Tables'],
      active: 'Basic',
    },
  ];

  errorMessage = signal<string>('');
  isLoading = signal<boolean>(false);

  constructor(private comunicacionService: ComunicacionService,
    private aprendizService: AprendizGrupoTeacherService,
    private registrarasistenciaService: RegistrarasistenciaService,
    public parametrosFindService : ParametrosFindService,
    public mediaService: MediaService,
    private dialog: MatDialog,

  ) { }

  ngOnInit(): void {
    this.comunicacionService.grupoCambiado$.subscribe(grupo => {
      this.grupo = grupo;
      this.getAprendices(grupo.id)
    });

    this.comunicacionService.seleccionAprendicesSinGrupo$.subscribe(value => {
      this.getAprendicesSinInscrpcion()
    });

    this.aprendizService.dataReadySubject.subscribe((dataReady: boolean) => {
      if (dataReady) {
        this.aprendizs = this.aprendizService.aprendiz;
      }
    });

    const hoy = new Date();
    this.fechaSeleccionada = hoy.toISOString().split('T')[0];
  }

  gotoEvaluacion(aprendiz: Aprendiz) {
    this.comunicacionService.informarCambioAprendiz(aprendiz)
    this.aprendizSelected.emit(aprendiz);
  }

  getAprendices(idgrupo: number) {
    this.aprendizService.searchAprendices(idgrupo );
  }

  getAprendicesSinInscrpcion() {
    this.aprendizService.searchAprendicessininscrpcion();
  }

  toggleDetail(index: number) {
    if (this.apredizSeleccionado === index) {
      this.apredizSeleccionado = -1;
    } else {
      this.apredizSeleccionado = index;
    }
  }

  onChangeAsistencia(asistencia: Asistencia, fechaProgramada: string) {
    try {
      asistencia.selected = !asistencia.selected;
      asistencia.fecha = fechaProgramada || this.fechaSeleccionada;
      asistencia.idaprendiz = this.aprendizs[this.apredizSeleccionado].id;

      this.isLoading.set(true);
      this.errorMessage.set('');

      this.registrarasistenciaService.registrar(asistencia).subscribe({
        next: (response) => {
          console.log('Asistencia registrada:', response);
          this.isLoading.set(false);
        },
        error: (error) => {
          console.error('Error al registrar asistencia:', error);
          this.errorMessage.set(error.message || 'Error al registrar la asistencia');
          this.isLoading.set(false);
          // Revertir el cambio en caso de error
          asistencia.selected = !asistencia.selected;
        }
      });

    } catch (error) {
      console.error('Error en el procesamiento de la fecha:', error);
      this.errorMessage.set(error.message || 'Error al procesar la fecha');
      // Revertir el cambio en caso de error
      asistencia.selected = !asistencia.selected;
    }
  }

  toggleFoto(index: number, idtrabajador: number): void {
    this.mediaService.searchFoto(idtrabajador);

    const interval = setInterval(() => {
      const foto = this.mediaService.fotoObtenida();
      if (foto) {
        clearInterval(interval);
        this.dialog.open(FotoModalComponent, {
          data: { fotoBase64: foto },
          width: '500px',
          height: 'auto'
        });
      }
    }, 100);
  }

  capturarFirma(idtrabajador: number, idaprendiz: number) {

    const dialogRef = this.dialog.open(CapturarFirmaComponent, {
      width: '800px',
      data: { idtrabajador, idaprendiz },
      disableClose: true
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        alert('Firma guardada exitosamente');
      }
    });
  }
}
