import { Component, OnInit, Input, EventEmitter, Output } from '@angular/core';

import { ComunicacionService } from '../../services/comunicacion.service';
import { tap } from 'rxjs';

import { ParametrosFindService } from '../../../core/service/parametrosFind.service';
import { UpdateParametrosEvaluationDateService } from '../../services/updateParametrosEvaluationDate.service';
import { ShowNotificacionService } from '../../../core/service/show-notificacion.service';
import { MatIconModule } from '@angular/material/icon';
import { NgFor } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { Grupo } from '../../../core/models/grupo.model';
import { Nivel } from '../../../core/models/nivel.model';
import { MatDialog } from '@angular/material/dialog';

import { NivelsService } from './../../../core/service/nivels.service';
import { UpdateInscriptionDateComponent } from '../update-inscription-date/update-inscription-date.component';

@Component({
    selector: 'teacher-grupos',
    templateUrl: './grupos.component.html',
    imports: [
        MatButtonModule,
        MatIconModule,
    ]
})
export class GruposComponent implements OnInit {
  @Input()
  public idprofesor: string;
  public grupos: Grupo[] | null  = [];
  public niveles: Nivel[] = [];

  @Output()
  grupoSelected: EventEmitter<Grupo> = new EventEmitter<Grupo>();

  breadscrums = [
    {
      title: 'Basic',
      items: ['Tables'],
      active: 'Basic',
    },
  ];
  constructor(
    private comunicacionService: ComunicacionService,
    private updateParametrosEvaluationDateService: UpdateParametrosEvaluationDateService,
    public parametrosFindService: ParametrosFindService,
    private notificacionService: ShowNotificacionService,
    private dialog: MatDialog,
    private nivelesService: NivelsService

  ) { }

  ngOnInit(): void {
    this.parametrosFindService.get();
  }

  gotoAprendiz(grupo: Grupo) {
    this.comunicacionService.informarCambioGrupo(grupo);
    this.grupoSelected.emit(grupo);
  }

  gotoAprendizSinGrupo() {
    this.comunicacionService.informarSeleccionAprendicesSinGrupo();
    this.grupoSelected.emit();
  }

  updateParametrosEvaluationDate() {
    this.updateParametrosEvaluationDateService.updateEvaluacionDate()
    .pipe(
      tap(response => {
        this.notificacionService.displaySuccess('Actualización exitosa');
      }),
    )
    .subscribe();
  }

  updateParametrosEncuestaDate() {
    this.updateParametrosEvaluationDateService.updateEncuestaDate()
    .pipe(
      tap(response => {
        this.notificacionService.displaySuccess('Actualización exitosa');
      }),    )
    .subscribe();
  }

  updateInscriptionDate2() {
    this.nivelesService.getNivelActivosinscripcion().subscribe({
      next: (niveles) => {
        // Una vez tengamos los datos, abrimos el modal
        const dialogRef = this.dialog.open(UpdateInscriptionDateComponent, {
          width: '920px',
          maxWidth: '96vw',
          maxHeight: '90vh',
          disableClose: true,
          data: { niveles }
        });

        dialogRef.afterClosed().subscribe(result => {
          if (result) {
            // Manejar el resultado si es necesario
          }
        });
      },
      error: (err) => {
        console.error('Error al cargar niveles:', err);
        // Mostrar mensaje de error si lo deseas
      }
    });
  }

  ngAfterViewInit() {

  }
}
