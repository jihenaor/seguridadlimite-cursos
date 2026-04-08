import { Component, Input, OnInit } from '@angular/core';
import { Pregunta } from 'src/app/core/models/pregunta.model';
import { NgIf, NgFor, NgStyle } from '@angular/common';

@Component({
    selector: 'app-respuestas',
    templateUrl: './respuestas.component.html',
    imports: [NgIf, NgFor, NgStyle]
})
export class RespuestasComponent implements OnInit {
  @Input()
  pregunta: Pregunta;

  constructor() { }

  ngOnInit(): void {
  }

}
