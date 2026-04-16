import { Component, EventEmitter, Input, Output, OnInit, AfterViewInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatButtonModule } from '@angular/material/button';
import { SvgIconComponent } from '../../../../../shared/components/svg-icon/svg-icon.component';
import { SelectionModel } from '@angular/cdk/collections';
import { MatDialog } from '@angular/material/dialog';
import { ServicesService } from '../../../../../core/service/services.service';
import { ModalViewComponent } from '../modal-view/modal-view.component';
import { ModalPermisoComponent } from '../modal-permiso/modal-permiso.component';
import { PermisoTrabajoAlturas } from '../../../../../core/models/permiso-trabajo.interface';
import { AprendicesMinisterioComponent } from '../aprendices-ministerio/aprendices-ministerio.component';

export interface PermisoTrabajo {
  id: number;
  codigoministerio: number;
  nombrenivel: string;
  fecha: string;
  validodesde: string;
  validohasta: string;
  nombrepersonaautoriza1: string;
  numerogrupos: number;
  dias: number;
  cupofinal: number;
}

@Component({
    selector: 'app-permiso-trabajo-tabla',
    templateUrl: './tabla.component.html',
    styleUrls: ['./tabla.component.scss'],
    imports: [
        CommonModule,
        MatTableModule,
        MatPaginatorModule,
        MatCheckboxModule,
        MatTooltipModule,
        MatButtonModule,
        SvgIconComponent
    ]
})
export class PermisoTrabajoTablaComponent implements OnInit, AfterViewInit {
  @Input() dataSource: PermisoTrabajo[] = [];
  @Input() cargando: boolean = false;
  @Output() verDetalleEvent = new EventEmitter<number>();
  @Output() editarPermisoEvent = new EventEmitter<number>();
  @Output() eliminarPermisoEvent = new EventEmitter<number>();
  @Output() showPdfEvent = new EventEmitter<{ id: number, grupo: number }>();
  @Output() listarPersonasEvent = new EventEmitter<number>();

  // Selection functionality
  selection = new SelectionModel<PermisoTrabajo>(true, []);
  selectedIds: number[] = [];

  displayedColumns: string[] = ['select', 'id', 
    'codigoministerio', 'nivel', 'fecha', 'validodesde', 
    'validohasta', 
    'nombrepersonaautoriza1', 
    'numerogrupos', 
    'acciones'];

  constructor(
    private dialog: MatDialog,
    public service: ServicesService,
  ) {}

  ngOnInit(): void {
    // Initialize any additional logic if needed
  }

  verDetalle(id: number): void {
    const dialogRef = this.dialog.open(ModalViewComponent, {
      width: '800px',
      data: { idPermiso: id }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.verDetalleEvent.emit(id);
      }
    });
  }

  editarPermiso(id: number): void {
    const dialogRef = this.dialog.open(ModalPermisoComponent, {
      width: '90%',
      maxWidth: '1200px',
      data: {
        action: 'edit',
        permiso: { idPermiso: id }
      },
      disableClose: true
    });

    dialogRef.afterClosed().subscribe((result: PermisoTrabajoAlturas) => {
      if (result) {
        // this.editarPermisoEvent.emit(id);
      }
    });
  }

  eliminarPermiso(id: number): void {
    this.eliminarPermisoEvent.emit(id);
  }

  /**
   * Mapea un objeto PermisoTrabajoAlturas a la interfaz PermisoTrabajo para la tabla
   * @param permiso Objeto PermisoTrabajoAlturas
   * @returns Objeto PermisoTrabajo formateado para la tabla
   */
  static mapearParaTabla(permiso: PermisoTrabajoAlturas): PermisoTrabajo {
    return {
      id: permiso.idPermiso || 0,
      codigoministerio: permiso.codigoministerio || 0,
      nombrenivel: permiso.nombrenivel || 'No especificado',
      fecha: permiso.fechaInicio || 'No especificada',
      validodesde: permiso.validodesde || 'No especificado',
      validohasta: permiso.validohasta || 'No especificado',
      nombrepersonaautoriza1: permiso.nombrepersonaautoriza1 || 'No especificado',
      numerogrupos: permiso.numerogrupos || 0,
      dias: permiso.dias || 0,
      cupofinal: permiso.cupofinal || 0
    };
  }

  async showPdf(id: number, grupo: number): Promise<void> {
    try {
      const r = await this.service.executeFetch(`/${id}/${grupo}/permisotrabajoalturas`);
      const linkSource = 'data:application/pdf;base64,' + r.base64 + '\n';
      const link = document.createElement('a');
      link.href = linkSource;
      link.download = `permiso_${id}_grupo${grupo}.pdf`;
      link.click();
    } catch (error) {
      console.error('Error al descargar el PDF:', error);
    }
    this.showPdfEvent.emit({ id, grupo });
  }

  listarPersonasAsociadas(id: number): void {
    this.listarPersonasEvent.emit(id);
  }

  // Selection methods
  isAllSelected(): boolean {
    const numSelected = this.selection.selected.length;
    const numRows = this.dataSource.length;
    return numSelected === numRows;
  }

  masterToggle(): void {
    this.isAllSelected() ?
      this.selection.clear() :
      this.dataSource.forEach(row => this.selection.select(row));
    
    this.updateSelectedIds();
  }

  updateSelectedIds(): void {
    this.selectedIds = this.selection.selected.map(item => item.id);
  }

  ngAfterViewInit(): void {
    this.selection.changed.subscribe(() => {
      this.updateSelectedIds();
    });
  }

  clearSelection(): void {
    this.selection.clear();
    this.selectedIds = [];
  }

  consultarMinisterio(): void {
    if (this.selectedIds.length > 0) {
      const dialogRef = this.dialog.open(AprendicesMinisterioComponent, {
        width: '90%',
        maxWidth: '1200px',
        height: '80vh',
        data: { selectedIds: this.selectedIds },
        disableClose: false
      });

      dialogRef.afterClosed().subscribe(result => {
        // Handle modal close if needed
      });
    }
  }
}
