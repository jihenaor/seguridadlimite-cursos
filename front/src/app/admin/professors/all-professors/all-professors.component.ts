import {
  AfterViewInit,
  Component,
  ViewChild,
  signal,
} from '@angular/core';
import { ProfessorsService } from './professors.service';
import { HttpClient } from '@angular/common/http';

import { MatSort, MatSortModule } from '@angular/material/sort';
import { Professors } from './../../../core/models/professors.model';

import { DataSource } from '@angular/cdk/collections';
import { BehaviorSubject, merge, Observable } from 'rxjs';
import { map, skip, take } from 'rxjs/operators';
import { FormDialogComponent } from './dialogs/form-dialog/form-dialog.component';
import { DeleteDialogComponent } from './dialogs/delete/delete.component';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AsyncPipe } from '@angular/common';
import { SvgIconComponent } from '../../../shared/components/svg-icon/svg-icon.component';

@Component({
  standalone: true,
  selector: 'app-all-professors',
  templateUrl: './all-professors.component.html',
  styleUrl: './all-professors.component.scss',
  imports: [
    AsyncPipe,
    SvgIconComponent,
    MatSortModule,
    MatPaginatorModule,
  ],
})
export class AllprofessorsComponent implements AfterViewInit {
  readonly searchTerm = signal('');
  readonly loading = signal(true);
  private loadSafetyTimeoutId: ReturnType<typeof setTimeout> | null = null;

  exampleDatabase: ProfessorsService | null = null;
  dataSource: ExampleDataSource | null = null;
  tableRows$: Observable<Professors[]> | null = null;

  id = 0;
  professors: Professors | null = null;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(
    public httpClient: HttpClient,
    public dialog: MatDialog,
    public professorsService: ProfessorsService,
    private snackBar: MatSnackBar
  ) {}

  ngAfterViewInit(): void {
    this.loadData();
  }

  isSi(v: string | undefined | null): boolean {
    return (v ?? '').toUpperCase() === 'S';
  }

  onSearchInput(value: string): void {
    this.searchTerm.set(value);
    if (this.dataSource) {
      this.dataSource.filter = value;
    }
  }

  get filteredTotal(): number {
    return this.dataSource?.filteredData.length ?? 0;
  }

  /** Muestra el pie con paginador solo si hay más registros que caben en la página actual. */
  get showPaginator(): boolean {
    const total = this.filteredTotal;
    if (total === 0) {
      return false;
    }
    const pageSize = this.paginator?.pageSize ?? 20;
    return total > pageSize;
  }

  get pageRangeLabel(): string {
    if (!this.dataSource || this.loading()) {
      return '';
    }
    const total = this.filteredTotal;
    if (!this.paginator || total === 0) {
      return 'Sin instructores para mostrar';
    }
    const start = this.paginator.pageIndex * this.paginator.pageSize + 1;
    const end = Math.min(
      (this.paginator.pageIndex + 1) * this.paginator.pageSize,
      total
    );
    return `Mostrando ${start}–${end} de ${total} instructores`;
  }

  refresh(): void {
    this.searchTerm.set('');
    if (this.dataSource) {
      this.dataSource.filter = '';
    }
    this.loading.set(true);
    this.loadData();
  }

  addNew(): void {
    const dialogRef = this.dialog.open(FormDialogComponent, {
      data: {
        professors: this.professors,
        action: 'add',
      },
    });
    dialogRef.afterClosed().subscribe((result) => {
      if (result === 1 && this.exampleDatabase) {
        this.exampleDatabase.dataChange.value.unshift(
          this.professorsService.getDialogData()
        );
        this.refreshTable();
        this.showNotification(
          'snackbar-success',
          'Registro creado correctamente.',
          'bottom',
          'center'
        );
      }
    });
  }

  editCall(row: Professors): void {
    this.id = row.id;
    const dialogRef = this.dialog.open(FormDialogComponent, {
      data: {
        professors: row,
        action: 'edit',
      },
    });
    dialogRef.afterClosed().subscribe((result) => {
      if (result === 1 && this.exampleDatabase) {
        const foundIndex = this.exampleDatabase.dataChange.value.findIndex(
          (x) => x.id === this.id
        );
        this.exampleDatabase.dataChange.value[foundIndex] =
          this.professorsService.getDialogData();
        this.refreshTable();
        this.showNotification(
          'black',
          'Registro actualizado correctamente.',
          'bottom',
          'center'
        );
      }
    });
  }

  deleteItem(row: Professors): void {
    this.id = row.id;
    const dialogRef = this.dialog.open(DeleteDialogComponent, {
      data: row,
    });
    dialogRef.afterClosed().subscribe((result) => {
      if (result === 1 && this.exampleDatabase) {
        const foundIndex = this.exampleDatabase.dataChange.value.findIndex(
          (x) => x.id === this.id
        );
        this.exampleDatabase.dataChange.value.splice(foundIndex, 1);
        this.refreshTable();
        this.showNotification(
          'snackbar-danger',
          'Registro eliminado correctamente.',
          'bottom',
          'center'
        );
      }
    });
  }

  private refreshTable(): void {
    if (this.paginator) {
      this.paginator._changePageSize(this.paginator.pageSize);
    }
  }

  loadData(): void {
    this.exampleDatabase = new ProfessorsService(this.httpClient);
    this.dataSource = new ExampleDataSource(
      this.exampleDatabase,
      this.paginator,
      this.sort
    );
    this.exampleDatabase.getAllProfessorss();
    this.tableRows$ = this.dataSource.connect();

    this.exampleDatabase.dataChange
      .pipe(skip(1), take(1))
      .subscribe({
        next: () => this.loading.set(false),
        error: () => this.loading.set(false),
      });
    if (this.loadSafetyTimeoutId) {
      clearTimeout(this.loadSafetyTimeoutId);
    }
    this.loadSafetyTimeoutId = setTimeout(() => {
      if (this.loading()) {
        this.loading.set(false);
      }
      this.loadSafetyTimeoutId = null;
    }, 12000);
  }

  showNotification(
    colorName: string,
    text: string,
    placementFrom: 'top' | 'bottom',
    placementAlign: 'start' | 'center' | 'end' | 'left' | 'right'
  ): void {
    this.snackBar.open(text, '', {
      duration: 2000,
      verticalPosition: placementFrom,
      horizontalPosition: placementAlign,
      panelClass: colorName,
    });
  }
}

export class ExampleDataSource extends DataSource<Professors> {
  _filterChange = new BehaviorSubject('');
  get filter(): string {
    return this._filterChange.value;
  }
  set filter(filter: string) {
    this._filterChange.next(filter);
  }
  filteredData: Professors[] = [];
  renderedData: Professors[] = [];
  constructor(
    public _exampleDatabase: ProfessorsService,
    public _paginator: MatPaginator,
    public _sort: MatSort
  ) {
    super();
    this._filterChange.subscribe(() => (this._paginator.pageIndex = 0));
  }

  connect(): Observable<Professors[]> {
    const displayDataChanges = [
      this._exampleDatabase.dataChange,
      this._sort.sortChange,
      this._filterChange,
      this._paginator.page,
    ];
    return merge(...displayDataChanges).pipe(
      map(() => {
        this.filteredData = this._exampleDatabase.data
          .slice()
          .filter((professors: Professors) => {
            const hay = (
              (professors.nombrecompleto ?? '') +
              (professors.numerodocumento ?? '') +
              (professors.numerocelular ?? '') +
              (professors.email ?? '')
            ).toLowerCase();
            return hay.indexOf(this.filter.toLowerCase()) !== -1;
          });
        const sortedData = this.sortData(this.filteredData.slice());
        const startIndex = this._paginator.pageIndex * this._paginator.pageSize;
        this.renderedData = sortedData.splice(
          startIndex,
          this._paginator.pageSize
        );
        return this.renderedData;
      })
    );
  }

  disconnect(): void {}

  sortData(data: Professors[]): Professors[] {
    if (!this._sort.active || this._sort.direction === '') {
      return data;
    }
    return data.sort((a, b) => {
      let propertyA: number | string = '';
      let propertyB: number | string = '';
      switch (this._sort.active) {
        case 'id':
          [propertyA, propertyB] = [a.id, b.id];
          break;
        case 'numerodocumento':
          [propertyA, propertyB] = [a.numerodocumento, b.numerodocumento];
          break;
        case 'nombrecompleto':
          [propertyA, propertyB] = [a.nombrecompleto, b.nombrecompleto];
          break;
        case 'entrenador':
          [propertyA, propertyB] = [a.entrenador, b.entrenador];
          break;
        case 'supervisor':
          [propertyA, propertyB] = [a.supervisor, b.supervisor];
          break;
        case 'email':
          [propertyA, propertyB] = [a.email, b.email];
          break;
        case 'numerocelular':
          [propertyA, propertyB] = [a.numerocelular, b.numerocelular];
          break;
        default:
          [propertyA, propertyB] = [a.nombrecompleto, b.nombrecompleto];
          break;
      }
      const valueA = isNaN(+propertyA) ? propertyA : +propertyA;
      const valueB = isNaN(+propertyB) ? propertyB : +propertyB;
      return (
        (valueA < valueB ? -1 : 1) * (this._sort.direction === 'asc' ? 1 : -1)
      );
    });
  }
}
