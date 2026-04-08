import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { EmpresasService } from './empresas.service';

import { FormDialogComponent } from './dialogs/form-dialog/form-dialog.component';

import { fromEvent } from 'rxjs';
import { debounceTime, distinctUntilChanged, map } from 'rxjs/operators';
import { MatDialog } from '@angular/material/dialog';
import { NgFor } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { PagetitleComponent } from '../../../shared/components/page-title/pagetitle.component';

@Component({
    selector: 'app-all-empresas',
    templateUrl: './all-empresas.component.html',
    imports: [
        PagetitleComponent,
        MatButtonModule,
        MatIconModule,
        NgFor,
    ]
})
export class AllEmpresasComponent implements OnInit {
  id: number;

  constructor(
    public dialog: MatDialog,
    public empresaService: EmpresasService,
  ) {
  }
  @ViewChild('filter', { static: true }) filter: ElementRef;

//  isExpansionDetailRow = (i: number, row: Curso) => this.expandedElement !== undefined && row.id === this.expandedElement.id;

  ngOnInit() {
    this.loadData();

    const searchInput = this.filter.nativeElement;

    fromEvent(searchInput, 'input')
      .pipe(
        map((event: Event) => (event.target as HTMLInputElement).value),
        debounceTime(300),
        distinctUntilChanged()
      )
      .subscribe((searchTerm: string) => {
        this.empresaService.filter(searchTerm);
      });
  }

  refresh() {
    this.loadData();
  }

  async addNew() {
    const dialogRef = this.dialog.open(FormDialogComponent, {
      data: {
        action: 'add',
      },
    });
    dialogRef.afterClosed().subscribe((result) => {
      if (result === 1) {
      }
    });
  }
  editCall(row) {
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

  public loadData() {
    this.empresaService.getAllEmpresas();
  }
}

