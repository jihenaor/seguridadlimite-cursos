import { AfterViewInit, Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { PreguntasService } from './preguntas.service';
import { HttpClient } from '@angular/common/http';

import { FormDialogComponent } from './dialogs/form-dialog/form-dialog.component';
import { Pregunta } from '../../../core/models/pregunta.model';
import { ActivatedRoute } from '@angular/router';

import { fromEvent } from 'rxjs';
import { debounceTime, distinctUntilChanged, map } from 'rxjs/operators';
import { MatDialog } from '@angular/material/dialog';
import { RespuestasComponent } from '../components/respuestas/respuestas.component';
import { NgFor, NgStyle, NgIf } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';

@Component({
    selector: 'app-all-preguntas',
    templateUrl: './all-preguntas.component.html',
    styleUrls: ['./all-preguntas.component.scss'],
    imports: [
        MatButtonModule,
        MatIconModule,
        NgFor,
        NgStyle,
        NgIf,
        RespuestasComponent,
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
  preguntaDetalle: number = -1;
  private isNivelInRoute = false;
  private isGrupoInRoute = false;

  colores = ["#FFC0CB", "#87CEEB", "#98FB98", "#E6E6FA", "#FF33A3"];
  asignacionesDeColores = new Map<string, string>();

  @ViewChild('filter', { static: true }) filter: ElementRef;

  constructor(
    public httpClient: HttpClient,
    public dialog: MatDialog,
    public preguntasService: PreguntasService,
    private route: ActivatedRoute
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
        this.preguntasService.filter(searchTerm);
      });
  }
  ngAfterViewInit() {

  }

  refresh() {
    this.loadData();
  }
  addNew() {
    const dialogRef = this.dialog.open(FormDialogComponent, {
      data: {
        action: 'add',
        idgrupo: this.idgrupo,
        pregunta: new Pregunta()
      },
    });
    dialogRef.afterClosed().subscribe((result) => {
      if (result === 1) {
        this.loadData();
      }
    });
  }

  editCall(row) {
    this.id = row.id;
    const dialogRef = this.dialog.open(FormDialogComponent, {
      data: {
        pregunta: row,
        action: 'edit',
      },
    });
    dialogRef.afterClosed().subscribe((result) => {
      if (result === 1) {
        this.loadData()
      }
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


  toggleDetail(index: number) {
    if (this.preguntaDetalle === index) {
      this.preguntaDetalle = -1; // Oculta el detalle si ya está visible
    } else {
      this.preguntaDetalle = index; // Muestra el detalle de la pregunta seleccionada
    }
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
