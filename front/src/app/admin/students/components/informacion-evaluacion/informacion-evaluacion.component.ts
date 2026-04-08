import { Component, OnInit, Input } from '@angular/core';
import { Aprendiz } from 'src/app/core/models/aprendiz.model';
import { SelectFormComponent } from '../select-form/select-form.component';
import { NgIf, DecimalPipe } from '@angular/common';

@Component({
    selector: 'aprendiz-informacion-evaluacion',
    templateUrl: './informacion-evaluacion.component.html',
    imports: [NgIf, SelectFormComponent, DecimalPipe]
})
export class InformacionEvaluacionComponent implements OnInit {
  @Input()
  aprendiz: Aprendiz;

  constructor() { }

  ngOnInit(): void {
  }

}
