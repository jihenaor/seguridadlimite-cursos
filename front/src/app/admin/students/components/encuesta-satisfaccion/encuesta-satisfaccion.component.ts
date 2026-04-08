import { Component, Input, OnInit } from '@angular/core';
import { Aprendiz } from '../../../../core/models/aprendiz.model';
import { Preguntaaprendiztipo } from '../../../../core/service/preguntaaprendiztipo.service';
import { NgFor, NgIf, KeyValuePipe } from '@angular/common';

@Component({
    selector: 'aprendiz-encuesta-satisfaccion',
    templateUrl: './encuesta-satisfaccion.component.html',
    styleUrls: ['./encuesta-satisfaccion.component.scss'],
    imports: [NgFor, NgIf, KeyValuePipe]
})
export class EncuestaSatisfaccionComponent implements OnInit {
  @Input()
  public aprendiz: Aprendiz;

  constructor(public preguntasService: Preguntaaprendiztipo) { }

  ngOnInit(): void {
    this.preguntasService.search(this.aprendiz.id + '', 'C', '0');
  }
}
