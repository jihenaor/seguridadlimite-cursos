import { Component, Input } from '@angular/core';
import { NgIf } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatButtonModule } from '@angular/material/button';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { Aprendiz } from '@model/aprendiz.model';
import { TomarFotoComponent } from '../../../../components/tomar-foto/tomar-foto.component';
import { CapturarFirmaComponent } from '../../../../components/capturar-firma/capturar-firma.component';

@Component({
    selector: 'app-aprendices-instructor-table',
    imports: [NgIf, MatIconModule, MatTooltipModule, MatButtonModule, MatDialogModule],
    template: `
    <div class="tableBody" *ngIf="aprendices.length > 0">
      <div class="table-responsive">
        <table class="table table-hover">
          <thead>
            <tr>
              <th>#</th>
              <th>Numero documento</th>
              <th>Nombre</th>
              <th>Código de verificación</th>
              <th>Nivel</th>
              <th>Fecha inscripcion</th>
              <th>Foto</th>
              <th>Firma</th>
            </tr>
          </thead>
          <tbody>
            @for (aprendiz of aprendices; track $index) {
              <tr>
                <th scope="row">{{ $index + 1 }}</th>
                <td>{{ aprendiz.numerodocumento }}</td>
                <td>{{ aprendiz.nombreCompletoTrabajador }}</td>
                <td>{{ aprendiz.codigoverificacion }}</td>
                <td>{{ aprendiz.nivel.nombre }}</td>
                <td>{{ aprendiz.fechainscripcion }}</td>
                <td>
                  <div class="button-group">
                    <button
                      mat-mini-fab
                      color="primary"
                      class="me-2"
                      matTooltip="Capturar Foto"
                      (click)="capturarFoto(aprendiz.idtrabajador)">
                      <mat-icon>photo_camera</mat-icon>
                    </button>
                  </div>
                </td>
                <td>
                  <div class="button-group">
                    <button
                      mat-mini-fab
                      color="accent"
                      matTooltip="Capturar Firma"
                      (click)="capturarFirma(aprendiz.idtrabajador, aprendiz.id)">
                      <mat-icon>draw</mat-icon>
                    </button>
                  </div>
                </td>
              </tr>
            }
          </tbody>
        </table>
      </div>
    </div>
  `,
    styles: [`
    .button-group {
      display: flex;
      gap: 8px;
    }

    .mat-mini-fab {
      transform: scale(0.8);
    }
  `]
})
export class AprendicesInstructorTableComponent {
  @Input() aprendices: Aprendiz[] = [];

  constructor(private dialog: MatDialog) {}

  capturarFoto(idtrabajador: number) {
    const dialogRef = this.dialog.open(TomarFotoComponent, {
      width: '800px',
      data: { idtrabajador },
      disableClose: true
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        // Aquí podemos manejar acciones después de guardar la foto
        console.log('Foto guardada exitosamente');
      }
    });
  }

  capturarFirma(idtrabajador: number, idaprendiz: number) {

    const dialogRef = this.dialog.open(CapturarFirmaComponent, {
      width: '800px',
      data: { idtrabajador, idaprendiz },
      disableClose: true
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        // Aquí podemos manejar acciones después de guardar la firma
        console.log('Firma guardada exitosamente');
      }
    });
  }
}
