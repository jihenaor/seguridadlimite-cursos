import { Component, OnInit } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { NgFor, NgIf, KeyValuePipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatButtonToggleModule } from '@angular/material/button-toggle';
import { tap, catchError, throwError } from 'rxjs';
import { Pregunta } from '../../../core/models/pregunta.model';
import { RespuestaEncuestaSatisfaccion } from '../../../core/models/respuestaencuestasatisfaccion.model';

import { PreguntasatisfaccionclienteService } from './../../services/preguntasatisfaccioncliente.service';
import { SaveencuestasatisfaccionclienteService } from './../../services/saveencuestassatisfaccioncliente.service';

@Component({
    templateUrl: './satisfaccioncliente.component.html',
    styleUrls: ['./satisfaccioncliente.component.scss'],
    imports: [NgFor, NgIf, MatButtonToggleModule, MatButtonModule, RouterLink, KeyValuePipe, FormsModule]
})
export class SatisfaccionclienteComponent implements OnInit {
  public idaprendiz: string = '';
  public numerodocumento: string = '';
  public nombreaprendiz: string = '';
  public mostrarInputRespuesta = false;
  public comentariosencuesta = '';

  constructor(
    public preguntasService: PreguntasatisfaccionclienteService,
    public saveencuestassatisfaccionclienteService: SaveencuestasatisfaccionclienteService,
    private router: Router,) { }

  ngOnInit(): void {
    this.idaprendiz = sessionStorage.getItem('idaprendiz')!;
    this.numerodocumento = sessionStorage.getItem('numerodocumento') || '';
    this.nombreaprendiz = sessionStorage.getItem('nombreaprendiz') || '';

    if (this.numerodocumento === null || this.numerodocumento === undefined || this.numerodocumento.length == 0) {
      alert("No existe numero de documento")
      this.router.navigate(['/quiz/inicio-satisfaccion-cliente']);
      return;
    }

    this.preguntasService.searchEncuestaSatisfaccionCliente(this.idaprendiz);

    // Esperar a que los datos estén listos y luego asignar el valor de comentarios
    this.preguntasService.dataReadySubject.subscribe({
      next: (isReady) => {
        if (isReady) {
          this.comentariosencuesta = this.preguntasService.comentariosencuesta || '';
          this.mostrarInputRespuesta = this.preguntasService.activacomentarios;
        }
      },
      error: (error) => {
        console.error('Error al cargar comentarios:', error);
      }
    });
  }

  onSeleccionarOpcionRespuesta(pregunta: Pregunta, event: string) {
    pregunta.respuestacorrecta = event;

    // Verificar si es la última pregunta y si seleccionó 'S'
    const esUltimaPregunta = this.esUltimaPregunta(pregunta);
    this.mostrarInputRespuesta = esUltimaPregunta && event === 'S';
  }

  private esUltimaPregunta(pregunta: Pregunta): boolean {
    // Implementar la lógica para determinar si es la última pregunta
    // Esto dependerá de tu estructura de datos
    const todasLasPreguntas = this.preguntasService.preguntas || [];
    return todasLasPreguntas[todasLasPreguntas.length - 1] === pregunta;
  }

  validarRespondeTodasLasPreguntas(): boolean {

    if (!this.preguntasService.preguntas || this.preguntasService.preguntas.length === 0) {
      return false;
    }

    for (const pregunta of this.preguntasService.preguntas) {
      if (!pregunta.respuestacorrecta) {
        alert('Por favor responder todas las preguntas (' + pregunta.pregunta + ")");
        return false;
      }
    }

    return true;
  }

  actualizarEncuesta() {
    if (!this.validarRespondeTodasLasPreguntas()) {
      return;
    }

    const respuestaEncuesta: RespuestaEncuestaSatisfaccion = {
      comentariosencuesta: this.comentariosencuesta,
      activacomentarios: this.mostrarInputRespuesta,
      preguntas: this.preguntasService.preguntas || []
    };

    this.saveencuestassatisfaccionclienteService.saveEvaluacion(respuestaEncuesta,
      Number(this.idaprendiz))
      .pipe(
        tap(() => {
          alert('Encuesta registrada con éxito');
          window.history.back();
        }),
        catchError(error => {
          alert('Se ha presentado un error')
          console.error(error);
          return throwError(() => error);
        })
      )
      .subscribe();
  }

  onComentariosChange(value: string): void {
    this.comentariosencuesta = value;
  }
}
