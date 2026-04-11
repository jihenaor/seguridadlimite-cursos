import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';

@Component({
    selector: 'quiz-buton-iniciar-evaluacion',
    standalone: true,
    templateUrl: './buton-iniciar-evaluacion.component.html',
    styleUrls: ['./buton-iniciar-evaluacion.component.scss'],
    imports: [MatButtonModule, MatIconModule],
})
export class ButonIniciarEvaluacionComponent implements OnInit {
  @Input()
  opciones: Record<string, boolean> = {};

  textoBoton: String;
  textoSalirSinEvaluar = 'Salir sin evaluar';

  constructor(private router: Router) { }

  ngOnInit(): void {
    if (this.opciones.evaluacionTeorico) {
      this.textoBoton = 'Iniciar evaluación';
      this.textoSalirSinEvaluar = 'No deseo presentar la evaluación';
    } else if (this.opciones.conocimientosPrevios) {
      this.textoBoton = 'Iniciar evaluación';
      this.textoSalirSinEvaluar = 'No deseo presentar la evaluación';
    } else if (this.opciones.satisfaccionCliente) {
      this.textoBoton = 'Iniciar encuesta';
      this.textoSalirSinEvaluar = 'No deseo responder la encuesta';
    }

  }

  /** Acción secundaria tipo cancelar: abandona el flujo de evaluación hacia inscripción. */
  salirSinEvaluar(): void {
    void this.router.navigate(['/student/inscription']);
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
