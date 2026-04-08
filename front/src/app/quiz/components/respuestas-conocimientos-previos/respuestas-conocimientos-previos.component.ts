import { Component, OnInit, Input } from '@angular/core';
import { Aprendiz } from 'src/app/core/models/aprendiz.model';
import { Preguntaaprendiztipo } from 'src/app/core/service/preguntaaprendiztipo.service';
import { MatIconModule } from '@angular/material/icon';
import { MatDividerModule } from '@angular/material/divider';
import { NgFor, NgIf } from '@angular/common';

@Component({
    selector: 'quiz-respuestas-conocimientos-previos',
    templateUrl: './respuestas-conocimientos-previos.component.html',
    styleUrls: ['./respuestas-conocimientos-previos.component.scss'],
    imports: [NgFor, NgIf, MatDividerModule, MatIconModule]
})
export class RespuestasConocimientosPreviosComponent implements OnInit {

  @Input()
  public aprendiz: Aprendiz;

  constructor(public preguntasService: Preguntaaprendiztipo) { }

  ngOnInit(): void {
    const tipoEvaluacion = 'I';

    this.preguntasService.search(this.aprendiz.id + '', tipoEvaluacion, '0');
  }
}
