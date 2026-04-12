import { Component, inject } from '@angular/core';
import { NgIf } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';

import { PreguntasService } from '../../preguntas.service';

@Component({
  selector: 'app-preguntas-enfasis-filter',
  standalone: true,
  imports: [NgIf, MatButtonModule, MatSlideToggleModule],
  templateUrl: './preguntas-enfasis-filter.component.html',
  styleUrl: './preguntas-enfasis-filter.component.scss',
})
export class PreguntasEnfasisFilterComponent {
  readonly preguntasService = inject(PreguntasService);

  readonly options = this.preguntasService.enfasisFilterOptions;

  clearEnfasis(): void {
    this.preguntasService.clearEnfasisFilter();
  }

  toggle(id: number): void {
    this.preguntasService.toggleEnfasisId(id);
  }

  isSelected(id: number): boolean {
    return this.preguntasService.selectedEnfasisIds().includes(id);
  }

  noneSelected(): boolean {
    return this.preguntasService.selectedEnfasisIds().length === 0;
  }
}
