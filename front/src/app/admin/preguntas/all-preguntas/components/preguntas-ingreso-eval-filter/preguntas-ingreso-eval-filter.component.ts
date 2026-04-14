import { Component, inject } from '@angular/core';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';

import { PreguntasService } from '../../preguntas.service';

@Component({
  selector: 'app-preguntas-ingreso-eval-filter',
  standalone: true,
  imports: [MatSlideToggleModule, MatFormFieldModule, MatSelectModule],
  templateUrl: './preguntas-ingreso-eval-filter.component.html',
  styleUrl: './preguntas-ingreso-eval-filter.component.scss',
})
export class PreguntasIngresoEvalFilterComponent {
  readonly preguntasService = inject(PreguntasService);

  diflecturaSelectValue(): string {
    const v = this.preguntasService.ingresoDiflecturaFilter();
    return v ?? '';
  }

  onDiflecturaChange(value: string): void {
    const v = value === '' ? null : (value as 'S' | 'N');
    this.preguntasService.setIngresoDiflecturaFilter(v);
  }
}
