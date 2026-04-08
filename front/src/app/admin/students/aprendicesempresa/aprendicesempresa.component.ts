import { AuthService } from './../../../core/service/auth.service';
import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { AprendicesempresaService } from './aprendicesempresa.service';
import { HttpClient } from '@angular/common/http';
import { MatSort, MatSortModule } from '@angular/material/sort';
import { DataSource } from '@angular/cdk/collections';
import { BehaviorSubject, fromEvent, merge, Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { SelectionModel } from '@angular/cdk/collections';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { Aprendiz } from '../../../core/models/aprendiz.model';
import { Router } from "@angular/router";
import { ServicesService } from 'src/app/core/service/services.service';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatMenuTrigger, MatMenuModule } from '@angular/material/menu';
import { MatDividerModule } from '@angular/material/divider';
import { MatRippleModule } from '@angular/material/core';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { NgClass, NgIf } from '@angular/common';
import { MatTableModule } from '@angular/material/table';
import { MatIconModule } from '@angular/material/icon';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatButtonModule } from '@angular/material/button';

@Component({
    selector: 'app-aprendicesempresa',
    templateUrl: './aprendicesempresa.component.html',
    styleUrls: ['./aprendicesempresa.component.sass'],
    imports: [
        RouterLink,
        MatButtonModule,
        MatTooltipModule,
        MatIconModule,
        MatTableModule,
        MatSortModule,
        NgClass,
        MatCheckboxModule,
        NgIf,
        MatRippleModule,
        MatMenuModule,
        MatDividerModule,
        MatPaginatorModule,
    ]
})
export class AprendicesempresaComponent implements OnInit {
  displayedColumns = [
    'select',
    'img',
    'numerodocumento',
    'nombrecompleto',
    'genero',
    'celular',
    'correoelectronico',
    'nivel',
    'enfasis',
    'estadoinscripcion',
    'semaforo',
    'actions',
  ];
  exampleDatabase: AprendicesempresaService | null;
  dataSource: ExampleDataSource | null;
  selection = new SelectionModel<Aprendiz>(true, []);
  id: number;
  students: Aprendiz | null;
  idempresa: number;

  constructor(
    public httpClient: HttpClient,
    public dialog: MatDialog,
    public aprendicesService: AprendicesempresaService,
    private snackBar: MatSnackBar,
    private route: ActivatedRoute,
    private router: Router,
    private services: ServicesService,
    private authService: AuthService
  ) {
    this.route.params.subscribe(params => {
      if (params['idempresa'] !== undefined) {
        this.idempresa = params['idempresa'];
      } else {
        this.idempresa = Number(this.authService.getItem('idempresa'));
      }
   });
  }
  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  @ViewChild(MatSort, { static: true }) sort: MatSort;
  @ViewChild('filter', { static: true }) filter: ElementRef;
  @ViewChild(MatMenuTrigger)
  contextMenu: MatMenuTrigger;
  contextMenuPosition = { x: '0px', y: '0px' };

  ngOnInit() {
    this.loadData();
  }
  refresh() {
    this.loadData();
  }

  gotoEvaluacionteorica(aprendiz: Aprendiz, numeroevaluacion: number) {
    // this.router.navigate(['/students/evaluacionteorica/' + this.idgrupo + '/' + aprendiz.id + '/' + numeroevaluacion]);
  }

  gotoEvaluacionpractica(aprendiz: Aprendiz) {
    sessionStorage.setItem('aprendiz_nombrecompleto', aprendiz.trabajador.nombrecompleto);
    // this.router.navigate(['/students/evaluacionpractica/' + this.idgrupo + '/' + aprendiz.id]);
  }

  async downloadEvaluacionpractica(aprendiz: Aprendiz) {
    const r = await this.services.executeFetch('/formatoevaluacionpractica/' + aprendiz.id);

    const linkSource = 'data:application/pdf;base64,' + r.base64 + '\n';
    const downloadLink = document.createElement("a");
    const fileName = "evaluacionpractica.pdf";

    downloadLink.href = linkSource;
    downloadLink.download = fileName;
    downloadLink.click();
  }

  private refreshTable() {
    this.paginator._changePageSize(this.paginator.pageSize);
  }
  /** Whether the number of selected elements matches the total number of rows. */
  isAllSelected() {
    const numSelected = this.selection.selected.length;
    const numRows = this.dataSource.renderedData.length;
    return numSelected === numRows;
  }

  /** Selects all rows if they are not all selected; otherwise clear selection. */
  masterToggle() {
    this.isAllSelected()
      ? this.selection.clear()
      : this.dataSource.renderedData.forEach((row) =>
        this.selection.select(row)
      );
  }

  public loadData() {
    this.exampleDatabase = new AprendicesempresaService(this.httpClient);
    if (this.idempresa) {
      this.aprendicesService.setFilterData({
        idempresa: this.idempresa,
      });
    }
    this.dataSource = new ExampleDataSource(
      this.exampleDatabase,
      this.paginator,
      this.sort,
      this.aprendicesService.getFilterData()
    );
    fromEvent(this.filter.nativeElement, 'keyup')
      // .debounceTime(150)
      // .distinctUntilChanged()
      .subscribe(() => {
        if (!this.dataSource) {
          return;
        }
        this.dataSource.filter = this.filter.nativeElement.value;
      });
  }

  showNotification(colorName, text, placementFrom, placementAlign) {
    this.snackBar.open(text, '', {
      duration: 2000,
      verticalPosition: placementFrom,
      horizontalPosition: placementAlign,
      panelClass: colorName,
    });
  }
  // context menu
  onContextMenu(event: MouseEvent, item: Aprendiz) {
    event.preventDefault();
    this.contextMenuPosition.x = event.clientX + 'px';
    this.contextMenuPosition.y = event.clientY + 'px';
    this.contextMenu.menuData = { item: item };
    this.contextMenu.menu.focusFirstItem('mouse');
    this.contextMenu.openMenu();
  }
}
export class ExampleDataSource extends DataSource<Aprendiz> {
  _filterChange = new BehaviorSubject('');
  get filter(): string {
    return this._filterChange.value;
  }
  set filter(filter: string) {
    this._filterChange.next(filter);
  }
  filteredData: Aprendiz[] = [];
  renderedData: Aprendiz[] = [];
  constructor(
    public _exampleDatabase: AprendicesempresaService,
    public _paginator: MatPaginator,
    public _sort: MatSort,
    public _filter: any
  ) {
    super();
    // Reset to the first page when the user changes the filter.
    this._filterChange.subscribe(() => (this._paginator.pageIndex = 0));
  }
  /** Connect function called by the table to retrieve one stream containing the data to render. */
  connect(): Observable<Aprendiz[]> {
    // Listen for any changes in the base data, sorting, filtering, or pagination
    const displayDataChanges = [
      this._exampleDatabase.dataChange,
      this._sort.sortChange,
      this._filterChange,
      this._paginator.page,
    ];
    this._exampleDatabase.getAllAprendices(this._filter);
    return merge(...displayDataChanges).pipe(
      map(() => {
        // Filter data
        this.filteredData = this._exampleDatabase.data
          .slice()
          .filter((students: Aprendiz) => {
            const searchStr = (
              students.id +
              students.trabajador.numerodocumento +
              students.trabajador.nombrecompleto +
              students.trabajador.correoelectronico +
              students.trabajador.celular
            ).toLowerCase();
            return searchStr.indexOf(this.filter.toLowerCase()) !== -1;
          });
        // Sort filtered data
        const sortedData = this.sortData(this.filteredData.slice());
        // Grab the page's slice of the filtered sorted data.
        const startIndex = this._paginator.pageIndex * this._paginator.pageSize;
        this.renderedData = sortedData.splice(
          startIndex,
          this._paginator.pageSize
        );
        return this.renderedData;
      })
    );
  }
  disconnect() { }
  /** Returns a sorted copy of the database data. */
  sortData(data: Aprendiz[]): Aprendiz[] {
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
        case 'name':
          [propertyA, propertyB] = [a.trabajador.nombrecompleto, b.trabajador.nombrecompleto];
          break;
        case 'correoelectronico':
          [propertyA, propertyB] = [a.trabajador.correoelectronico, b.trabajador.correoelectronico];
          break;
        case 'time':
          [propertyA, propertyB] = [a.trabajador.correoelectronico, b.trabajador.correoelectronico];
          break;
        case 'celular':
          [propertyA, propertyB] = [a.trabajador.celular, b.trabajador.celular];
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
