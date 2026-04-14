import { Component, inject } from '@angular/core';
import { NgIf } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';

import { PreguntasService } from '../../preguntas.service';

@Component({
  selector: 'app-preguntas-nivel-filter',
  standalone: true,
  imports: [NgIf, MatButtonModule],
  templateUrl: './preguntas-nivel-filter.component.html',
  styleUrl: './preguntas-nivel-filter.component.scss',
})
export class PreguntasNivelFilterComponent {
  readonly preguntasService = inject(PreguntasService);

  readonly options = this.preguntasService.nivelFilterOptions;

  clearNivel(): void {
    this.preguntasService.clearNivelFilter();
  }

  toggle(id: number): void {
    this.preguntasService.toggleNivelId(id);
  }

  isSelected(id: number): boolean {
    return this.preguntasService.selectedNivelIds().includes(id);
  }

  noneSelected(): boolean {
    return this.preguntasService.selectedNivelIds().length === 0;
  }
}
