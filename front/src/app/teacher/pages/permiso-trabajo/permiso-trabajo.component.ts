import { Component, OnInit, computed, effect } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { SvgIconComponent } from '../../../shared/components/svg-icon/svg-icon.component';

import { PermisoTrabajoFiltrosComponent } from './components/filtros/filtros.component';
import { PermisoTrabajoTablaComponent } from './components/tabla/tabla.component';
import { ModalPermisoComponent } from './components/modal-permiso/modal-permiso.component';
import { PermisoTrabajoSignalsService } from '../../../core/service/permiso-trabajo-signals.service';
import { FiltroPermiso } from './components/filtros/filtros.component';
import { PermisoTrabajoAlturas } from '../../../core/models/permiso-trabajo.interface';

// Interfaz para la tabla de permisos
export interface PermisoTrabajo {
  id: number;
  tipo: string;
  fecha: string;
  cliente: string;
  estado: string;
}

@Component({
    selector: 'app-permiso-trabajo',
    templateUrl: './permiso-trabajo.component.html',
    imports: [
        CommonModule,
        RouterLink,
        MatDialogModule,
        MatSnackBarModule,
        SvgIconComponent,
        PermisoTrabajoFiltrosComponent,
        PermisoTrabajoTablaComponent
    ]
})
export class PermisoTrabajoComponent implements OnInit {
  // Computed signal para transformar los permisos a formato de tabla
  dataSource = computed(() => {
    return this.permisoService.permisos().map(permiso =>
      PermisoTrabajoTablaComponent.mapearParaTabla(permiso)
    );
  });

  // Fechas por defecto para la consulta inicial
  private fechaActual = new Date();
  private fechaInicio = new Date(this.fechaActual.getFullYear(), this.fechaActual.getMonth(), 1)
    .toISOString().split('T')[0]; // Primer día del mes actual
  private fechaFin = new Date(this.fechaActual.getFullYear(), this.fechaActual.getMonth() + 1, 0)
    .toISOString().split('T')[0]; // Último día del mes actual

  constructor(
    private dialog: MatDialog,
    public permisoService: PermisoTrabajoSignalsService,
    private snackBar: MatSnackBar
  ) {
    // Efecto para mostrar mensajes de error
    effect(() => {
      const errorMsg = this.permisoService.error();
      if (errorMsg) {
        this.mostrarMensaje(errorMsg);
        this.permisoService.limpiarError();
      }
    });
  }

  ngOnInit(): void {
    // Cargar permisos iniciales (del mes actual)
    this.permisoService.consultarPorFechas(this.fechaInicio, this.fechaFin);
  }

  aplicarFiltro(filtros: FiltroPermiso): void {
    // Prioridad 1: Si hay código ministerio, usar el nuevo endpoint específico
    if (filtros.codigoministerio && filtros.codigoministerio.trim() !== '') {
      this.permisoService.consultarPorCodigoMinisterio(filtros.codigoministerio.trim());
    } 
    // Prioridad 2: Si hay fechas del filtro, usarlas
    else if (filtros.fechaDesde || filtros.fechaHasta) {
      const fechaDesde = filtros.fechaDesde ?
        new Date(filtros.fechaDesde).toISOString().split('T')[0] :
        this.fechaInicio;

      const fechaHasta = filtros.fechaHasta ?
        new Date(filtros.fechaHasta).toISOString().split('T')[0] :
        this.fechaFin;

      this.permisoService.consultarPorFechas(fechaDesde, fechaHasta);
    } 
    // Prioridad 3: Usar fechas por defecto
    else {
      this.permisoService.consultarPorFechas(this.fechaInicio, this.fechaFin);
    }
  }

  verDetalle(id: number): void {
    console.log('Ver detalle del permiso:', id);
    // Obtener el permiso por ID
    this.permisoService.obtenerPermisoPorId(id);

    // Esperar a que se cargue el permiso y luego abrir el modal
    const checkPermiso = setInterval(() => {
      const permiso = this.permisoService.permisoSeleccionado();
      if (permiso) {
        clearInterval(checkPermiso);
        this.abrirModalPermiso('view', permiso);
        this.permisoService.limpiarPermisoSeleccionado();
      }
    }, 100);

    // Timeout para evitar esperar indefinidamente
    setTimeout(() => {
      clearInterval(checkPermiso);
      if (!this.permisoService.permisoSeleccionado()) {
        this.mostrarMensaje('No se pudo cargar el detalle del permiso');
      }
    }, 5000);
  }

  editarPermiso(permisoId:  number): void {
   // Esperar a que se cargue el permiso y luego abrir el modal
    const checkPermiso = setInterval(() => {
        clearInterval(checkPermiso);
        this.abrirModalPermiso('edit', permisoId);
        this.permisoService.limpiarPermisoSeleccionado();

    }, 100);

    // Timeout para evitar esperar indefinidamente
    setTimeout(() => {
      clearInterval(checkPermiso);
      if (!this.permisoService.permisoSeleccionado()) {
        this.mostrarMensaje('No se pudo cargar el permiso para editar');
      }
    }, 5000);
  }

  eliminarPermiso(permiso: PermisoTrabajo | number): void {
    const permisoId = typeof permiso === 'number' ? permiso : permiso.id;

    if (confirm('¿Está seguro de eliminar este permiso?')) {
      this.permisoService.eliminarPermiso(permisoId).subscribe(
        () => {
          this.mostrarMensaje('Permiso eliminado correctamente');
        },
        (error) => {
          console.error('Error al eliminar el permiso:', error);
        }
      );
    }
  }

  abrirModalPermiso(action: string, permiso?: PermisoTrabajoAlturas | number): void {
    const dialogRef = this.dialog.open(ModalPermisoComponent, {
      width: '90%',
      maxWidth: '1200px',
      data: {
        action: action,
        permiso: permiso || {}
      },
      disableClose: true
    });

    dialogRef.afterClosed().subscribe((result: PermisoTrabajoAlturas) => {
      if (result) {
        console.log('Permiso guardado:', result);
        // Recargar los permisos con los filtros actuales
        this.permisoService.consultarPorFechas(this.fechaInicio, this.fechaFin);
      }
    });
  }

  mostrarMensaje(mensaje: string): void {
    this.snackBar.open(mensaje, 'Cerrar', {
      duration: 3000,
      horizontalPosition: 'center',
      verticalPosition: 'bottom'
    });
  }

}
