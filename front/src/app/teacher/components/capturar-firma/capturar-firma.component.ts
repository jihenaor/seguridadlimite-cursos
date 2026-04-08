import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA, MatDialogModule } from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button';
import { FirmaComponent } from '../../../admin/students/components/firma/firma.component';
import { NgIf } from '@angular/common';

@Component({
    selector: 'app-capturar-firma',
    imports: [MatDialogModule, MatButtonModule, FirmaComponent],
    template: `
    <div class="modal-container">
      <h2 mat-dialog-title>Capturar Firma</h2>

      <mat-dialog-content>
        <app-firma [idtrabajador]="data.idtrabajador" [idaprendiz]="data.idaprendiz"></app-firma>
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
export class CapturarFirmaComponent {
  constructor(
    public dialogRef: MatDialogRef<CapturarFirmaComponent>,
    @Inject(MAT_DIALOG_DATA) public data: {
      idtrabajador: number;
      idaprendiz: number;
    }
  ) {}

  cerrar() {
    this.dialogRef.close();
  }

  guardarYCerrar() {
    // Aquí podríamos agregar lógica adicional antes de cerrar
    this.dialogRef.close(true);
  }
}
