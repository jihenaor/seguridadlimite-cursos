import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { MatSort, MatSortModule } from '@angular/material/sort';
import { DataSource } from '@angular/cdk/collections';
import { BehaviorSubject, fromEvent, merge, Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { ActivatedRoute, RouterLink } from '@angular/router';
import { Router } from "@angular/router";
import { EvaluacionteoricaService } from './evaluacionteorica.service';
import { ServicesService } from '../../../core/service/services.service';
import { Evaluacion } from './../../../core/models/evaluacion.model';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatMenuTrigger, MatMenuModule } from '@angular/material/menu';
import { MatRippleModule } from '@angular/material/core';
import { MatTooltipModule } from '@angular/material/tooltip';
import { NgClass, NgIf } from '@angular/common';
import { MatTableModule } from '@angular/material/table';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';

@Component({
    selector: 'app-evaluacionteorica',
    templateUrl: './evaluacionteorica.component.html',
    styleUrls: ['./evaluacionteorica.component.sass'],
    imports: [
        RouterLink,
        MatButtonModule,
        MatIconModule,
        MatTableModule,
        MatSortModule,
        NgClass,
        NgIf,
        MatTooltipModule,
        MatRippleModule,
        MatMenuModule,
        MatPaginatorModule,
    ]
})
export class EvaluacionteoricaComponent implements OnInit {
  displayedColumns = [
    'bloque',
    'pregunta',
    'r'
  ];
  exampleDatabase: EvaluacionteoricaService | null;
  dataSource: ExampleDataSource | null;
  id: number;
  students: Evaluacion | null;
  idaprendiz: number;
  numeroevaluacion: number;
  npreguntas = 0;
  naprobadas = 0;
  nfallidas = 0;
  nota;
  idgrupo: number;
  idnivel: number;
  nombretrabajador: string;

  constructor(
    public httpClient: HttpClient,
    public dialog: MatDialog,
    public asistenciaService: EvaluacionteoricaService,
    private services: ServicesService,
    private snackBar: MatSnackBar,
    private route: ActivatedRoute,
    private router: Router
  ) {
    this.route.params.subscribe(params => {
      if (params['idaprendiz'] !== undefined) {
        this.idaprendiz = params['idaprendiz'];
      }
      if (params['numeroevaluacion'] !== undefined) {
        this.numeroevaluacion = params['numeroevaluacion'];
      }
      if (params['idgrupo'] !== undefined) {
        this.idgrupo = params['idgrupo'];
      }
      if (params['idnivel'] !== undefined) {
        this.idnivel = params['idnivel'];
      }
      if (params['nombretrabajador'] !== undefined) {
        this.nombretrabajador = params['nombretrabajador'];
      }
   });
  }
  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator;
  @ViewChild(MatSort, { static: true }) sort: MatSort;
  @ViewChild(MatMenuTrigger)
  contextMenu: MatMenuTrigger;
  contextMenuPosition = { x: '0px', y: '0px' };

  async ngOnInit() {
    this.loadData();
  }

  async actualizar() {
    const r = await this.services.post('/updateEvaluacionaprendiz', this.dataSource.renderedData);

    if (r.code === '200') {
      alert('Actualización exitosa');
    } else {
      alert('Code: ' + r.code + '.  Error ' + r.msg);
    }
  }

  /** Whether the number of selected elements matches the total number of rows. */

  seleccionarRespuesta(evaluacion: Evaluacion, numero: number) {
    evaluacion.numerorespuesta = numero;

    if (numero === evaluacion.pregunta.numerorespuestacorrecta) {
      evaluacion.respuestacorrecta = 'S';
    } else {
      evaluacion.respuestacorrecta = 'N';
    }
    this.contarRespuestas();
  }

  contarRespuestas() {
    this.naprobadas = 0;
    this.nfallidas = 0;

    this.dataSource.renderedData.forEach((key: any, val: any) => {
      if (key.respuestacorrecta === 'S') {
        this.naprobadas++;
      } else {
        this.nfallidas++;
      }
    });

    this.nota = ((this.naprobadas) / this.dataSource.renderedData.length) * 5;
  }

  aprobarTodas() {
    this.dataSource.renderedData.forEach((key: any, val: any) => {
      key.respuestacorrecta = 'S';
      key.numerorespuesta = key.pregunta.numerorespuestacorrecta;
    });
    this.nota = 5;
    this.naprobadas = this.dataSource.renderedData.length;
    this.nfallidas = 0;
  }

  public loadData() {
    this.exampleDatabase = new EvaluacionteoricaService(this.httpClient);

    if (this.idaprendiz) {
      this.asistenciaService.setFilterData({
        idaprendiz: this.idaprendiz,
        numeroevaluacion: this.numeroevaluacion,
        idnivel: this.idnivel,
      });
    }

    this.dataSource = new ExampleDataSource(
      this.exampleDatabase,
      this.paginator,
      this.sort,
      this.asistenciaService.getFilterData()
    );
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
  onContextMenu(event: MouseEvent, item: Evaluacion) {
    event.preventDefault();
    this.contextMenuPosition.x = event.clientX + 'px';
    this.contextMenuPosition.y = event.clientY + 'px';
    this.contextMenu.menuData = { item: item };
    this.contextMenu.menu.focusFirstItem('mouse');
    this.contextMenu.openMenu();
  }
}
export class ExampleDataSource extends DataSource<Evaluacion> {
  _filterChange = new BehaviorSubject('');
  get filter(): string {
    return this._filterChange.value;
  }
  set filter(filter: string) {
    this._filterChange.next(filter);
  }
  filteredData: Evaluacion[] = [];
  renderedData: Evaluacion[] = [];
  constructor(
    public _exampleDatabase: EvaluacionteoricaService,
    public _paginator: MatPaginator,
    public _sort: MatSort,
    public _filter: any
  ) {
    super();
    // Reset to the first page when the user changes the filter.
    this._filterChange.subscribe(() => (this._paginator.pageIndex = 0));
  }
  /** Connect function called by the table to retrieve one stream containing the data to render. */
  connect(): Observable<Evaluacion[]> {
    // Listen for any changes in the base data, sorting, filtering, or pagination
    const displayDataChanges = [
      this._exampleDatabase.dataChange,
      this._sort.sortChange,
      this._filterChange,
      this._paginator.page,
    ];
    this._exampleDatabase.getEvaluacionGrupo(this._filter);
    return merge(...displayDataChanges).pipe(
      map(() => {
        // Filter data
        this.filteredData = this._exampleDatabase.data
          .slice()
          .filter((students: Evaluacion) => {
            const searchStr = (
              students.id +
              ''
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
  sortData(data: Evaluacion[]): Evaluacion[] {
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
      }
      const valueA = isNaN(+propertyA) ? propertyA : +propertyA;
      const valueB = isNaN(+propertyB) ? propertyB : +propertyB;
      return (
        (valueA < valueB ? -1 : 1) * (this._sort.direction === 'asc' ? 1 : -1)
      );
    });
  }
}
