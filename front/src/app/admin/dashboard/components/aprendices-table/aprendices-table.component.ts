import { Component, Input } from '@angular/core';
import { NgIf } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatButtonModule } from '@angular/material/button';
import { Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { ResumenInscritosModalComponent } from '../resumen-inscritos-modal/resumen-inscritos-modal.component';
import { Aprendiz } from '@model/aprendiz.model';

@Component({
    selector: 'app-aprendices-table',
    imports: [NgIf, MatIconModule, MatTooltipModule, MatButtonModule],
    template: `
    <div class="d-flex justify-content-end mb-3" *ngIf="aprendices.length > 0">
      <button mat-raised-button color="primary" (click)="mostrarResumen()">
        <mat-icon>assessment</mat-icon>
        Ver Resumen
      </button>
    </div>

    <div class="tableBody" *ngIf="aprendices.length > 0">
      <div class="table-responsive">
        <table class="table table-hover">
          <thead>
            <tr>
              <th>#</th>
              <th>Numero documento</th>
              <th>Nombre</th>
              <th>Código de verificación</th>
              <th>id Permiso / código ministerio</th>              
              <th>Nivel</th>
              <th>Fecha inscrip/Ult asistencia</th>
              <th>Fecha fin grupo</th>
              <th>PAGADO</th>
              <th></th>
              <td>id T A</td>
            </tr>
          </thead>
          <tbody>
            @for (aprendiz of aprendices; track $index) {
              <tr>
                <th scope="row">{{ $index + 1 }}</th>
                <td>{{ aprendiz.numerodocumento }}</td>
                <td>{{ aprendiz.nombreCompletoTrabajador }}</td>
                <td>{{ aprendiz.codigoverificacion }}</td>
                <td>{{ aprendiz.idpermiso}} / {{ aprendiz.codigoministerio }}</td>
                <td>{{ aprendiz.nivel.nombre }}</td>
                <td>{{ aprendiz.fechainscripcion }}</td>
                <td></td>
                <td>
                  @if (aprendiz.pagocurso === 'S') {
                    <mat-icon class="text-success" matTooltip="Pagado">check_circle</mat-icon>
                  } @else {
                    <mat-icon class="text-danger" matTooltip="Pendiente de pago">error</mat-icon>
                  }
                </td>
                <td>
                  <button mat-mini-fab color="primary" (click)="irTrabajador(aprendiz.idtrabajador, aprendiz.id)">
                    <mat-icon aria-label="Edit" class="col-white">edit</mat-icon>
                  </button>
                </td>
                <td>{{ aprendiz.idtrabajador }} - {{ aprendiz.id }}</td>
              </tr>
            }
          </tbody>
        </table>
      </div>
    </div>
  `
})
export class AprendicesTableComponent {
  @Input() aprendices: Aprendiz[] = [];

  constructor(
    private router: Router,
    private dialog: MatDialog
  ) {}

  mostrarResumen() {
    this.dialog.open(ResumenInscritosModalComponent, {
      width: '600px',
      data: { aprendices: this.aprendices }
    });
  }

  irTrabajador(idtrabajador: number, idaprendiz: number) {
    try {
      sessionStorage.setItem('idtrabajador', idtrabajador + '');
      sessionStorage.setItem('idaprendiz', idaprendiz + '');

      this.router.navigate(['/admin/students/about-aprendiz', idaprendiz]);
    } catch(error) {
      alert('Error al abrir la página para tomar la foto' + error);
    }
  }
}
