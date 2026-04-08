import { Component, Inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatDialogRef, MAT_DIALOG_DATA, MatDialogModule } from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { PermisoTrabajoAlturas } from '../../../../../core/models/permiso-trabajo.interface';
import { PermisoTrabajoService } from '../../../../../core/service/permiso-trabajo.service';

@Component({
    selector: 'app-modal-view',
    templateUrl: './modal-view.component.html',
    imports: [
        CommonModule,
        MatDialogModule,
        MatButtonModule,
        MatIconModule,
        MatProgressSpinnerModule
    ]
})
export class ModalViewComponent implements OnInit {
  permiso: PermisoTrabajoAlturas | null = null;
  error: string | null = null;

  constructor(
    public dialogRef: MatDialogRef<ModalViewComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { idPermiso: number },
    private permisoTrabajoService: PermisoTrabajoService
  ) { }

  ngOnInit(): void {
    this.cargarPermiso();
  }

  cargarPermiso(): void {
    this.error = null;
    this.permisoTrabajoService.consultarPermiso(this.data.idPermiso)
      .subscribe({
        next: (permiso) => {
          this.permiso = permiso;
        },
        error: (error) => {
          this.error = 'Error al cargar el permiso';
          console.error('Error:', error);
        }
      });
  }

  cerrarModal(): void {
    this.dialogRef.close();
  }
}
