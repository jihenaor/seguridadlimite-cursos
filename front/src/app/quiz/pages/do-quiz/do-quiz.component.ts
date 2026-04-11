import { TipoQuiz } from './../../../core/models/tipo-quiz';
import { MessageService } from './../../../core/service/message.service';
import { Component } from '@angular/core';
import { tap, catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';

import { PreguntaaprendizService } from './../../services/preguntaaprendiz.service';
import { SaveevaluacionService } from './../../services/saveevaluacion.service';

import { RespuestaEvaluacion } from './../../interfaces/quiz.interfaces';
import { ActivatedRoute, Router } from '@angular/router';
import { Respuesta } from 'src/app/core/models/respuesta.model';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { HttpInterceptorService } from '../../../core/interceptor/http.interceptor';
import { ShowNotificacionService } from '../../../core/service/show-notificacion.service';
import { MatButtonModule } from '@angular/material/button';
import { NgIf, NgFor, NgStyle } from '@angular/common';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';

@Component({
    selector: 'app-do-quiz',
    templateUrl: './do-quiz.component.html',
    styleUrls: ['./do-quiz.component.scss'],
    imports: [
        MatCardModule,
        MatToolbarModule,
        NgIf,
        NgFor,
        NgStyle,
        MatButtonModule,
        MatIconModule,
    ]
})
export class DoQuizComponent {
  public idaprendiz: string = '';
  public numerodocumento: string = '';
  public nombreaprendiz: string = '';
  public questionList: RespuestaEvaluacion[] = [];
  public tipoQuiz: TipoQuiz;
  public error;

  interval$: any;
  progress: string = '0';
  isQuizCompleted: boolean = false;

  constructor(
    private preguntaaprendizService: PreguntaaprendizService,
    private saveevaluacionService: SaveevaluacionService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private sanitizer: DomSanitizer,
    private messageService: MessageService,
    private httpInterceptorService: HttpInterceptorService,
    private notificacionService: ShowNotificacionService
    ) {
      const currentRoute = this.activatedRoute.snapshot.routeConfig?.path;

      // Verificar si la ruta actual es "conocimientosprevios"
      if (currentRoute === 'conocimientosprevios') {
        this.tipoQuiz = TipoQuiz.INGRESO
        this.updateSidebarText()
      } else {
        this.tipoQuiz = TipoQuiz.CONOCIMIENTOSTEORICOS;
      }
    }

  ngOnInit(): void {
    this.idaprendiz = sessionStorage.getItem('idaprendiz')!;
    this.numerodocumento = sessionStorage.getItem('numerodocumento') || '';
    this.nombreaprendiz = sessionStorage.getItem('nombreaprendiz') || '';

    if (this.numerodocumento === null || this.numerodocumento.length == 0) {
      alert("No existe numero de documento")
      this.router.navigate(['/quiz/inicio']);
      return;
    }

    this.httpInterceptorService.error$.subscribe(error => {
      this.error = error;

      if (this.error) {
        alert(this.error);
      }
    });

//    Validar evaluacion realizada

    this.preguntaaprendizService.dataReadySubject.subscribe((dataReady: boolean) => {
      if (dataReady) {
        this.questionList = this.preguntaaprendizService.respuestaEvaluacion;
      }
    });

    const idaprendiz = Number(sessionStorage.getItem('idaprendiz'));

    if (! idaprendiz || idaprendiz === 0) {
      alert('Debe seleccionar un aprendiz')
      return;
    }

    switch(this.tipoQuiz) {
      case TipoQuiz.INGRESO:
        this.preguntaaprendizService.searchPreguntasaprendizIngreso(idaprendiz);
        break;
      case TipoQuiz.CONOCIMIENTOSTEORICOS:
        this.preguntaaprendizService.searchPreguntasaprendizEvaluacionTeorica(idaprendiz);
        break;
    }

  }

  answer(respuesta: RespuestaEvaluacion, option: any) {
    respuesta.respuestas.forEach(respuesta => {
      respuesta.respondida = false;
    });

    respuesta.respuestacorrecta = option.numero === respuesta.numerorespuestacorrecta ? 'S' : 'N';

    respuesta.numerorespuesta = option.numero;
    respuesta.textorespuesta = option.respuesta;

    respuesta.respondida = true;
    option.respondida = true;

    this.getProgressPercent();
  }

  getProgressPercent() {
    this.progress = ((1 / this.questionList.length) * 100)
      .toFixed(0)
      .toString();

    return this.progress;
  }

  guardarEvaluacion(): void {
    let endpoint = ''
   /*
    if (currentQno === this.questionList.length) {
      const hayRespuestasSinDatos = this.questionList.some(function(pregunta) {
        return pregunta.respuestacorrecta === "";
      });

      if (hayRespuestasSinDatos) {
        alert("Hay respuestas sin responder");
      } else {
        this.isQuizCompleted = true;

      }
    }
*/

    let sinResponder = false;
    this.questionList.forEach(question => {
      let preguntaRespondida = false;

      question.respuestas.forEach(respuesta => {
        if (respuesta.respondida) {
          preguntaRespondida = true;
        }
      });

      if (preguntaRespondida == false) {
        sinResponder = true;
      }
    });

    if (sinResponder) {
      this.notificacionService.displayWarning("Existen preguntas sin responder");
      return;
    }

    switch(this.tipoQuiz) {
      case TipoQuiz.INGRESO:
        endpoint = 'registrarevaluacioningreso';
        break;
      case TipoQuiz.CONOCIMIENTOSTEORICOS:
        endpoint = 'registrarevaluacionteorica';
        break;
    }

    this.saveevaluacionService.saveEvaluacion(this.questionList,
      Number(this.idaprendiz), endpoint)
      .pipe(
        tap(response => {
          this.notificacionService.displaySuccess('La evaluación a finalizado');
          setTimeout(() => {
            this.router.navigate(['/quiz/finish/' + this.tipoQuiz ]);
           }, 1500);

        }),
        catchError(error => {
          alert('Se ha presentado un error')
          console.error(error);
          return throwError(() => error);
        })
      )
      .subscribe();
  }

  transformFoto(respuesta: Respuesta): SafeResourceUrl {
    if (respuesta.tieneimagen === 'S' && respuesta.nombreimagen) {
      const safeUrl = 'data:image/png;base64,' + respuesta.base64;
      return this.sanitizer.bypassSecurityTrustResourceUrl(safeUrl);
    }
    return '';
  }

  transformFotoPregunta(question: RespuestaEvaluacion): SafeResourceUrl {
    if (question.base64) {
      const safeUrl = 'data:image/png;base64,' + question.base64;
      return this.sanitizer.bypassSecurityTrustResourceUrl(safeUrl);
    }
    return '';
  }

  updateSidebarText() {
    const newMessage = 'Evaluación de conocimiento';
    this.messageService.updateMessage(newMessage);
  }

  /** Vuelve a la pantalla de inicio del tipo de evaluación actual (misma sesión). */
  regresar(): void {
    if (this.tipoQuiz === TipoQuiz.INGRESO) {
      void this.router.navigate(['/quiz/inicio-conocimientos-previos']);
      return;
    }
    void this.router.navigate(['/quiz/inicio-teorico']);
  }

}
