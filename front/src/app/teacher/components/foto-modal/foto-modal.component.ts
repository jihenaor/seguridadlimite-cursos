import { Component, Inject } from '@angular/core';
import { MatDialogModule, MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button';

@Component({
    selector: 'app-foto-modal', // Esto lo convierte en un componente standalone
    imports: [MatDialogModule, MatButtonModule],
    template: `
    <h2 mat-dialog-title>Foto del Trabajador</h2>
    <mat-dialog-content>
      <img [src]="'data:image/png;base64,' + data.fotoBase64" alt="Foto del Trabajador" class="modal-img" />
    </mat-dialog-content>
    <mat-dialog-actions align="end">
      <button mat-button (click)="onClose()">Cerrar</button>
    </mat-dialog-actions>
  `,
    styles: [`
    .modal-img {
      width: 100%;
      max-height: 400px;
      object-fit: contain;
    }
  `]
})
export class FotoModalComponent {
  constructor(
    public dialogRef: MatDialogRef<FotoModalComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { fotoBase64: string }
  ) {}

  onClose(): void {
    this.dialogRef.close();
  }
}

