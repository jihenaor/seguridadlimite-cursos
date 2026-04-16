import { AfterViewInit, Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { PreguntasService } from './preguntas.service';
import { HttpClient } from '@angular/common/http';

import {
  PreguntaFormDialogComponent,
  PreguntaFormDialogData,
} from './dialogs/pregunta-form-dialog/pregunta-form-dialog.component';
import { Pregunta } from '../../../core/models/pregunta.model';
import { ActivatedRoute } from '@angular/router';

import { fromEvent } from 'rxjs';
import { debounceTime, distinctUntilChanged, map } from 'rxjs/operators';
import { MatDialog } from '@angular/material/dialog';
import { RespuestasComponent } from '../components/respuestas/respuestas.component';
import { PreguntasEnfasisFilterComponent } from './components/preguntas-enfasis-filter/preguntas-enfasis-filter.component';
import { PreguntasNivelFilterComponent } from './components/preguntas-nivel-filter/preguntas-nivel-filter.component';
import { PreguntasIngresoEvalFilterComponent } from './components/preguntas-ingreso-eval-filter/preguntas-ingreso-eval-filter.component';
import { PagetitleComponent } from '../../../shared/components/page-title/pagetitle.component';
import { NgFor, NgStyle, NgIf } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { SvgIconComponent } from '../../../shared/components/svg-icon/svg-icon.component';
import { PreguntasRegistrarService } from './preguntaregistrar.service';
import { ShowNotificacionService } from '../../../core/service/show-notificacion.service';

@Component({
    selector: 'app-all-preguntas',
    templateUrl: './all-preguntas.component.html',
    styleUrls: ['./all-preguntas.component.scss'],
    imports: [
        MatButtonModule,
        SvgIconComponent,
        NgFor,
        NgStyle,
        NgIf,
        RespuestasComponent,
        PreguntasEnfasisFilterComponent,
        PreguntasNivelFilterComponent,
        PreguntasIngresoEvalFilterComponent,
        PagetitleComponent,
    ]
})
export class AllpreguntasComponent implements OnInit, AfterViewInit {
  id: number;
  preguntas: Pregunta | null;
  mostrarRespuestas: boolean;
  idnivel: number;
  type: number;
  nombrenivel: string;
  idgrupo: string
  nombregrupo: string;
  /** Fila de respuestas abierta (por id de pregunta); null = ninguna. */
  preguntaDetalleId: number | null = null;
  private isNivelInRoute = false;
  /** Ruta `all-preguntas/grupo`: muestra filtros por énfasis. */
  readonly isGrupoInRoute: boolean;

  colores = ["#FFC0CB", "#87CEEB", "#98FB98", "#E6E6FA", "#FF33A3"];
  asignacionesDeColores = new Map<string, string>();

  @ViewChild('filter', { static: true }) filter: ElementRef;

  constructor(
    public httpClient: HttpClient,
    public dialog: MatDialog,
    public preguntasService: PreguntasService,
    private route: ActivatedRoute,
    private preguntasRegistrarService: PreguntasRegistrarService,
    private notificacionService: ShowNotificacionService
  ) {
    const urlSegments = this.route.snapshot.url.map(segment => segment.path);
    this.isNivelInRoute = urlSegments.includes('nivel');
    this.isGrupoInRoute = urlSegments.includes('grupo');

    if (this.isNivelInRoute) {
      this.route.params.subscribe(params => {
        this.idnivel = params['idnivel'];
        this.type = params['type'];
        this.nombrenivel = params['nombrenivel'];
      });
    } else {
      this.idgrupo = sessionStorage.getItem('idgrupopregunta');
      this.nombregrupo = sessionStorage.getItem('nombregrupopregunta');
    }
  }

  ngOnInit() {
    this.mostrarRespuestas = true;
    this.loadData();

    const searchInput = this.filter.nativeElement;

    fromEvent(searchInput, 'input')
      .pipe(
        map((event: Event) => (event.target as HTMLInputElement).value),
        debounceTime(300),
        distinctUntilChanged()
      )
      .subscribe((searchTerm: string) => {
        this.preguntasService.setTextFilter(searchTerm);
      });
  }
  ngAfterViewInit() {

  }

  refresh() {
    this.loadData();
  }
  addNew() {
    const data: PreguntaFormDialogData = {
      action: 'add',
      idgrupo: this.idgrupo,
      // Opcional: alto del toolbar (título + cerrar), p. ej. '3rem' o '52px'. Por defecto 48px en el diálogo.
      // headerChromeHeight: '3rem',
    };
    const dialogRef = this.dialog.open(PreguntaFormDialogComponent, { data });
    dialogRef.afterClosed().subscribe((result) => {
      if (result === 1) {
        this.loadData();
      }
    });
  }

  editCall(row) {
    this.id = row.id;
    const data: PreguntaFormDialogData = {
      action: 'edit',
      pregunta: row,
      // headerChromeHeight: '44px',
    };
    const dialogRef = this.dialog.open(PreguntaFormDialogComponent, { data });
    dialogRef.afterClosed().subscribe((result) => {
      if (result === 1) {
        this.loadData()
      }
    });
  }

  deleteCall(row: Pregunta): void {
    const msg =
      '¿Eliminar esta pregunta? Se borrarán sus respuestas y las evaluaciones registradas para esa pregunta.';
    if (!globalThis.confirm(msg)) {
      return;
    }
    this.preguntasRegistrarService.deletePregunta(row.id).subscribe({
      next: () => {
        this.notificacionService.displaySuccess('Pregunta eliminada');
        this.loadData();
      },
    });
  }

  public loadData() {
    if (this.isNivelInRoute) {
      this.preguntasService.getPreguntasNivelTipoEvaluacion(this.idnivel + '', this.type + '');
    }
    if (this.isGrupoInRoute) {
      this.preguntasService.getPreguntasGrupo(this.idgrupo);
    }

  }


  toggleDetailByRow(row: Pregunta): void {
    const id = row.id;
    this.preguntaDetalleId = this.preguntaDetalleId === id ? null : id;
  }

  isDetailOpen(row: Pregunta): boolean {
    return this.mostrarRespuestas || this.preguntaDetalleId === row.id;
  }

  /** Vista tabla agrupada por énfasis (solo ruta grupo). */
  groupedTable(): boolean {
    return this.isGrupoInRoute && this.preguntasService.groupByEnfasis();
  }

  /** Oculta la columna énfasis cuando ya se agrupa por bloque. */
  hideEnfasisColumn(): boolean {
    return this.groupedTable();
  }

  colspanForTable(): number {
    return this.hideEnfasisColumn() ? 5 : 6;
  }

  trackByPreguntaId(_: number, row: Pregunta): number {
    return row.id;
  }

  get preguntasPageTitle(): string {
    return `Preguntas: ${this.nombrenivel ?? ''}${this.nombregrupo ?? ''}`;
  }

  toggleAllDetails() {
    this.mostrarRespuestas = !this.mostrarRespuestas;
  }

  getColorClass(agrupador1: string): string {
    if (!this.asignacionesDeColores.has(agrupador1)) {
      // Si el valor de agrupador1 no tiene un color asignado, asigna uno aleatoriamente.
      const colorAleatorio = this.colores[Math.floor(Math.random() * this.colores.length)];
      this.asignacionesDeColores.set(agrupador1, colorAleatorio);
    }

    return this.asignacionesDeColores.get(agrupador1);

  }



}
