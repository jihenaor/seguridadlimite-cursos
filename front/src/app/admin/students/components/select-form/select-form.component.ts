import { Component, Input, OnInit } from '@angular/core';
import { Aprendiz } from '../../../../core/models/aprendiz.model';
import { EncuestaSatisfaccionComponent } from '../encuesta-satisfaccion/encuesta-satisfaccion.component';
import { EvaluacionPracticaComponent } from '../evaluacion-practica/evaluacion-practica.component';
import { EvaluacionTeoricaComponent } from '../evaluacion-teorica/evaluacion-teorica.component';
import { ConocimientosPreviosComponent } from '../conocimientos-previos/conocimientos-previos.component';
import { MatButtonModule } from '@angular/material/button';
import { NgFor, NgIf } from '@angular/common';

@Component({
    selector: 'aprendiz-select-form',
    templateUrl: './select-form.component.html',
    styleUrls: ['./select-form.component.scss'],
    imports: [NgFor, MatButtonModule, NgIf, ConocimientosPreviosComponent, EvaluacionTeoricaComponent, EvaluacionPracticaComponent, EncuestaSatisfaccionComponent]
})
export class SelectFormComponent implements OnInit {
  @Input()
  public aprendiz: Aprendiz;

  itemSelected: string;
  items: any[]; // Define los botones y componentes aquí

  constructor() {

    this.items = [
      { label: 'Conocimientos previos', component: 'conocimientosPrevios' },
      { label: 'Evaluación teórica', component: 'evaluacionTeorica' },
      { label: 'Evaluación práctica', component: 'evaluacionPractica' },
      { label: 'Encuesta satisfacción', component: 'encuestaSatisfaccion' }
    ];
  }

  ngOnInit(): void {
  }

  toggleComponent(item: any) {
    this.itemSelected = item.component;
  }

}
