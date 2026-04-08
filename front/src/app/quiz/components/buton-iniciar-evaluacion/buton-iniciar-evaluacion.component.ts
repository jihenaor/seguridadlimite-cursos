import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';

@Component({
    selector: 'quiz-buton-iniciar-evaluacion',
    templateUrl: './buton-iniciar-evaluacion.component.html',
    imports: [MatButtonModule, MatIconModule]
})
export class ButonIniciarEvaluacionComponent implements OnInit {
  @Input()
  opciones: Record<string, boolean> = {};

  textoBoton: String;

  constructor(private router: Router) { }

  ngOnInit(): void {
    if (this.opciones.evaluacionTeorico) {
      this.textoBoton = 'Iniciar evaluación';
    } else if (this.opciones.conocimientosPrevios) {
      this.textoBoton = 'Iniciar evaluación';
    } else if (this.opciones.satisfaccionCliente) {
      this.textoBoton = 'Iniciar encuesta'
    }

  }

  onClick() {
    if (this.opciones.evaluacionTeorico) {
      this.router.navigate(['quiz/quiz'])
    } else if (this.opciones.conocimientosPrevios) {
      this.router.navigate(['/quiz/conocimientosprevios'])
    } else if (this.opciones.satisfaccionCliente) {
      this.router.navigate(['/quiz/satisfaccioncliente'])
    }
  }
}
