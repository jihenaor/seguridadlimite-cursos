import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { InscripcionesAbiertasService } from '../../../core/service/inscripciones-abiertas.service';

@Component({
  selector: 'app-inscripciones-abiertas',
  standalone: true,
  imports: [CommonModule],
  template: `
      <div class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded relative" role="alert">
        <strong class="font-bold">¡Atención!</strong>
        <span class="block sm:inline"> Las inscripciones se encuentran cerradas en este momento.</span>
      </div>

  `
})
export class InscripcionesAbiertasComponent {

  constructor() {}


}
