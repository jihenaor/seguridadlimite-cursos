import { Component, Input, OnInit } from '@angular/core';

import { AprendizEvaluacionService } from '../../services/aprendizevaluacion.service';

/** Mínimo de caracteres del documento para consultar la API (coherente con el backend). */
const MIN_DOC = 4;

@Component({
    selector: 'quiz-search-aprendiz',
    standalone: true,
    templateUrl: './search-aprendiz.component.html',
    styleUrls: ['./search-aprendiz.component.scss'],
})
export class SearchAprendizComponent implements OnInit {
  @Input()
  opciones: Record<string, boolean> = {};

  nombreSesion = '';
  documentoSesion = '';

  constructor(private aprendizService: AprendizEvaluacionService) {}

  ngOnInit(): void {
    this.nombreSesion = (sessionStorage.getItem('nombreaprendiz') || '').trim();
    this.documentoSesion = (sessionStorage.getItem('numerodocumento') || '').trim();

    if (this.documentoSesion.length >= MIN_DOC) {
      this.buscarPorSesion();
    }
  }

  private buscarPorSesion(): void {
    const doc = this.documentoSesion.trim();
    if (this.opciones.evaluacionTeorico) {
      this.aprendizService.searchAprendizevaluacion(doc, 'teorico');
    } else if (this.opciones.conocimientosPrevios) {
      this.aprendizService.searchAprendizevaluacion(doc, 'conocimientosprevios');
    } else if (this.opciones.satisfaccionCliente) {
      this.aprendizService.searchAprendizevaluacion(doc, 'satisfaccioncliente');
    }
  }
}
