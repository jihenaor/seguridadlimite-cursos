import { Component, Input, OnInit } from '@angular/core';
import { Aprendiz } from 'src/app/core/models/aprendiz.model';
import { NgIf } from '@angular/common';

@Component({
    selector: 'quiz-show-aprendiz',
    standalone: true,
    templateUrl: './show-aprendiz.component.html',
    imports: [NgIf],
})
export class ShowAprendizComponent {
  @Input()
  public aprendiz: Aprendiz | null;

  @Input()
  opciones: Record<string, boolean> = {};

  public mensaje: string;

}
