import { Component, Input, OnInit } from '@angular/core';
import { Aprendiz } from '../../../../core/models/aprendiz.model';
import { Preguntaaprendiztipo } from 'src/app/core/service/preguntaaprendiztipo.service';
import { NgFor } from '@angular/common';

@Component({
    selector: 'aprendiz-evaluacion-teorica',
    templateUrl: './evaluacion-teorica.component.html',
    styleUrls: ['./evaluacion-teorica.component.scss'],
    imports: [NgFor]
})
export class EvaluacionTeoricaComponent implements OnInit {

  @Input()
  aprendiz: Aprendiz

  constructor(public preguntasService: Preguntaaprendiztipo) { }

  ngOnInit(): void {
    const numero = this.aprendiz.eteorica2 > 0 ? '2' : '1';

    this.preguntasService.search(this.aprendiz.id + '', 'T', numero);
  }

}
