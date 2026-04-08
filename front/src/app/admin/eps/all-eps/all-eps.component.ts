import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { map } from 'rxjs/operators';
import { BehaviorSubject, fromEvent, merge, Observable, of } from 'rxjs';

import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { animate, state, style, transition, trigger } from '@angular/animations';

import { EpsService } from './eps.service';
import { HttpClient } from '@angular/common/http';
import { MatSort, MatSortModule } from '@angular/material/sort';
import { Eps } from '../../../core/models/eps.model';
import { DataSource } from '@angular/cdk/collections';
import { FormDialogComponent } from './dialogs/form-dialog/form-dialog.component';
import { DeleteDialogComponent } from './dialogs/delete/delete.component';
import { ActivatedRoute } from '@angular/router';
import { ServicesService } from '../../../core/service/services.service';
import { MatRippleModule } from '@angular/material/core';
import { MatTableModule } from '@angular/material/table';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { PagetitleComponent } from '../../../shared/components/page-title/pagetitle.component';

@Component({
    selector: 'app-all-eps',
    templateUrl: './all-eps.component.html',
    animations: [
        trigger('detailExpand', [
            state('collapsed', style({ height: '0px', minHeight: '0', visibility: 'hidden' })),
            state('expanded', style({ height: '*', visibility: 'visible' })),
            transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
        ]),
    ],
    imports: [
        PagetitleComponent,
        MatButtonModule,
        MatIconModule,
        MatTableModule,
        MatSortModule,
        MatRippleModule,
        MatPaginatorModule,
    ]
})
export class AllEpsComponent implements OnInit {
  displayedColumns = [
'nombre',
    'actions',
  ];
  exampleDatabase: EpsService | null;
  dataSource: ExampleDataSource | null;

  id: number;
  eps: Eps | null;

  expandedElement: Eps;

  constructor(
    public httpClient: HttpClient,
    public dialog: MatDialog,
    public epsService: EpsService,
    private snackBar: MatSnackBar,
    private route: ActivatedRoute,
    public service: ServicesService
  ) {
    this.route.params.subscribe(params => {

   });
  }
  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  @ViewChild(MatSort, { static: true }) sort: MatSort;
  @ViewChild('filter', { static: true }) filter: ElementRef;

  ngOnInit() {
    this.loadData();
  }
  refresh() {
    this.loadData();
  }

  async addNew() {
    const dialogRef = this.dialog.open(FormDialogComponent, {
      data: {
        eps: this.eps,
        action: 'add',
      },
    });
    dialogRef.afterClosed().subscribe((result) => {
      if (result === 1) {
        // After dialog is closed we're doing frontend updates
        // For add we're just pushing a new row inside DataServicex
        this.exampleDatabase.dataChange.value.unshift(
          this.epsService.getDialogData()
        );
        this.refreshTable();
        this.showNotification(
          'snackbar-success',
          'Add Record Successfully...!!!',
          'bottom',
          'center'
        );
      }
    });
  }
  editCall(row) {
    this.id = row.id;
    const dialogRef = this.dialog.open(FormDialogComponent, {
      data: {
        curso: row,
        action: 'edit',
      },
    });
    dialogRef.afterClosed().subscribe((result) => {
      if (result === 1) {
        // When using an edit things are little different, firstly we find record inside DataService by id
        const foundIndex = this.exampleDatabase.dataChange.value.findIndex(
          (x) => x.id === this.id
        );
        // Then you update that record using data from dialogData (values you enetered)
        this.exampleDatabase.dataChange.value[
          foundIndex
        ] = this.epsService.getDialogData();
        // And lastly refresh table
        this.refreshTable();
        this.showNotification(
          'black',
          'Edit Record Successfully...!!!',
          'bottom',
          'center'
        );
      }
    });
  }
  deleteItem(row) {
    this.id = row.id;
    const dialogRef = this.dialog.open(DeleteDialogComponent, {
      data: row,
    });
    dialogRef.afterClosed().subscribe((result) => {
      if (result === 1) {
        const foundIndex = this.exampleDatabase.dataChange.value.findIndex(
          (x) => x.id === this.id
        );
        // for delete we use splice in order to remove single object from DataService
        this.exampleDatabase.dataChange.value.splice(foundIndex, 1);
        this.refreshTable();
        this.showNotification(
          'snackbar-danger',
          'Delete Record Successfully...!!!',
          'bottom',
          'center'
        );
      }
    });
  }
  private refreshTable() {
    this.paginator._changePageSize(this.paginator.pageSize);
  }

  public loadData() {
    this.exampleDatabase = new EpsService(this.httpClient);
    this.dataSource = new ExampleDataSource(
      this.exampleDatabase,
      this.paginator,
      this.sort
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
}
export class ExampleDataSource extends DataSource<Eps> {
  _filterChange = new BehaviorSubject('');
  get filter(): string {
    return this._filterChange.value;
  }
  set filter(filter: string) {
    this._filterChange.next(filter);
  }
  filteredData: Eps[] = [];
  renderedData: Eps[] = [];
  constructor(
    public _exampleDatabase: EpsService,
    public _paginator: MatPaginator,
    public _sort: MatSort
  ) {
    super();
    // Reset to the first page when the user changes the filter.
    this._filterChange.subscribe(() => (this._paginator.pageIndex = 0));
  }
  /** Connect function called by the table to retrieve one stream containing the data to render. */
  connect(): Observable<Eps[]> {
    // Listen for any changes in the base data, sorting, filtering, or pagination
    const displayDataChanges = [
      this._exampleDatabase.dataChange,
      this._sort.sortChange,
      this._filterChange,
      this._paginator.page,
    ];
    this._exampleDatabase.getAllEps();
    return merge(...displayDataChanges).pipe(
      map(() => {
        // Filter data
        this.filteredData = this._exampleDatabase.data
          .slice()
          .filter((eps: Eps) => {
            const searchStr = (
              eps.id +
              eps.nombre
            ).toLowerCase();
            return searchStr.indexOf(this.filter.toLowerCase()) !== -1;
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
  disconnect() { }

  sortData(data: Eps[]): Eps[] {
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
        case 'nombre':
          [propertyA, propertyB] = [a.nombre, b.nombre];
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
