import { Component, OnInit, Input } from '@angular/core';
import { Preguntaaprendiztipo } from 'src/app/core/service/preguntaaprendiztipo.service';
import { Aprendiz } from 'src/app/core/models/aprendiz.model';
import { MatIconModule } from '@angular/material/icon';
import { MatDividerModule } from '@angular/material/divider';
import { NgFor, NgIf } from '@angular/common';

@Component({
    selector: 'quiz-respuestas-teorico',
    templateUrl: './respuestas-teorico.component.html',
    styleUrls: ['./respuestas-teorico.component.scss'],
    imports: [NgFor, NgIf, MatDividerModule, MatIconModule]
})
export class RespuestasTeoricoComponent implements OnInit {
  @Input()
  public aprendiz: Aprendiz;

  constructor(public preguntasService: Preguntaaprendiztipo) { }

  ngOnInit(): void {
    const numero = this.aprendiz.eteorica2 > 0 ? '2' : '1';
    const tipoEvaluacion = 'T';

    this.preguntasService.search(this.aprendiz.id + '', tipoEvaluacion, numero);
  }
}
