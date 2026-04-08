import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA, MatDialogModule } from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button';
import { PhotoComponent } from '../../../admin/students/components/photo/photo.component';
import { NgIf } from '@angular/common';

@Component({
    selector: 'app-tomar-foto',
    imports: [MatDialogModule, MatButtonModule, PhotoComponent],
    template: `
    <div class="modal-container">
      <h2 mat-dialog-title>Tomar Foto</h2>

      <mat-dialog-content>
        <app-photo [idtrabajador]="data.idtrabajador"></app-photo>
      </mat-dialog-content>

      <mat-dialog-actions align="end">
        <button mat-button (click)="cerrar()">Cancelar</button>
        <button mat-raised-button color="primary" (click)="guardarYCerrar()">
          Aceptar
        </button>
      </mat-dialog-actions>
    </div>
  `,
    styles: [`
    .modal-container {
      padding: 20px;
    }
    mat-dialog-actions {
      margin-top: 20px;
    }
  `]
})
export class TomarFotoComponent {
  constructor(
    public dialogRef: MatDialogRef<TomarFotoComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { idtrabajador: number }
  ) {}

  cerrar() {
    this.dialogRef.close();
  }

  guardarYCerrar() {
    // Aquí podríamos agregar lógica adicional antes de cerrar
    this.dialogRef.close(true);
  }
}
