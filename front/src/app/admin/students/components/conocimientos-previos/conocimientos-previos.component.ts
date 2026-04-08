import { Component, OnInit, Input } from '@angular/core';
import { Aprendiz } from '../../../../core/models/aprendiz.model';
import { Preguntaaprendiztipo } from '../../../../core/service/preguntaaprendiztipo.service';
import { NgFor } from '@angular/common';

@Component({
    selector: 'aprendiz-conocimientos-previos',
    templateUrl: './conocimientos-previos.component.html',
    styleUrls: ['./conocimientos-previos.component.scss'],
    imports: [NgFor]
})
export class ConocimientosPreviosComponent implements OnInit {
  @Input()
  aprendiz: Aprendiz

  constructor(public preguntasService: Preguntaaprendiztipo) { }

  ngOnInit(): void {
    this.preguntasService.search(this.aprendiz.id + '', 'I', '0');
  }
}
