import { Component, Input, OnInit } from '@angular/core';
import { Preguntaaprendiztipo } from '../../../../core/service/preguntaaprendiztipo.service';
import { Aprendiz } from '../../../../core/models/aprendiz.model';
import { NgFor, KeyValuePipe } from '@angular/common';

@Component({
    selector: 'aprendiz-evaluacion-practica',
    templateUrl: './evaluacion-practica.component.html',
    styleUrls: ['./evaluacion-practica.component.scss'],
    imports: [NgFor, KeyValuePipe]
})
export class EvaluacionPracticaComponent implements OnInit {
  @Input()
  public aprendiz!: Aprendiz;

  constructor(public preguntasService: Preguntaaprendiztipo) { }

  ngOnInit(): void {
    this.preguntasService.search(this.aprendiz.id + '', 'P', '0');
  }
}
