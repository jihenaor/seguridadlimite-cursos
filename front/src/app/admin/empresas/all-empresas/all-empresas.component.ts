import { Component, OnInit, signal } from '@angular/core';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';

import { EmpresasService } from './empresas.service';
import { FormDialogComponent } from './dialogs/form-dialog/form-dialog.component';
import { Empresa } from '../../../core/models/empresa.model';
import { SvgIconComponent } from '../../../shared/components/svg-icon/svg-icon.component';

@Component({
  standalone: true,
  selector: 'app-all-empresas',
  templateUrl: './all-empresas.component.html',
  imports: [MatDialogModule, SvgIconComponent],
})
export class AllEmpresasComponent implements OnInit {
  id!: number;
  readonly searchTerm = signal('');

  constructor(
    public dialog: MatDialog,
    public empresaService: EmpresasService,
  ) {}

  ngOnInit(): void {
    this.loadData();
  }

  onSearchInput(value: string): void {
    this.searchTerm.set(value);
    this.empresaService.filter(value);
  }

  refresh(): void {
    this.searchTerm.set('');
    this.empresaService.filter('');
    this.loadData();
  }

  addNew(): void {
    const dialogRef = this.dialog.open(FormDialogComponent, {
      data: {
        action: 'add',
      },
    });
    dialogRef.afterClosed().subscribe((result) => {
      if (result === 1) {
        this.empresaService.getAllEmpresas();
      }
    });
  }

  editCall(row: Empresa): void {
    this.id = row.id;
    const dialogRef = this.dialog.open(FormDialogComponent, {
      data: {
        empresa: row,
        action: 'edit',
      },
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result === 1) {
        this.empresaService.getAllEmpresas();
      }
    });
  }

  /** Sin menú contextual por defecto (evita el menú nativo del navegador). */
  onContextMenu(event: MouseEvent, _row: Empresa): void {
    event.preventDefault();
  }

  /** El borrado en backend no está cableado en este módulo; mantiene la UI coherente. */
  deleteItem(_row: Empresa): void {
    void _row;
  }

  loadData(): void {
    this.empresaService.getAllEmpresas();
  }
}
