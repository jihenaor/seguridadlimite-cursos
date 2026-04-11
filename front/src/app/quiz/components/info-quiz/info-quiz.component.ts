import { Component, Input, OnInit } from '@angular/core';
import { NgFor } from '@angular/common';

@Component({
    selector: 'quiz-info',
    standalone: true,
    templateUrl: './info-quiz.component.html',
    imports: [NgFor],
})
export class InfoQuizComponent implements OnInit {
  @Input()
  opciones: Record<string, boolean> = {};

  textoTitulo: string;

  listaItems: string[] = [];

  constructor() {}

  ngOnInit(): void {
    if (this.opciones.evaluacionTeorico) {
      this.textoTitulo = 'Pon a prueba tu aprendizaje del componente teórico';

      this.listaItems = [
        'Este examen tiene 10 preguntas para responder en 30 minutos',
        'Este examen puede presentarse hasta dos veces',
        'Debe aprobar el 80% de las preguntas',
      ];
    } else if (this.opciones.conocimientosPrevios) {
      this.textoTitulo = 'Evaluación de conocimientos previos';
    } else if (this.opciones.satisfaccionCliente) {
      this.textoTitulo = 'FORMATO EVALUACIÓN DE LA SATISFACCIÓN DEL CLIENTE';
    }
  }
}
