import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { MessageService } from 'src/app/core/service/message.service';

import { Aprendiz } from 'src/app/core/models/aprendiz.model';

import { AprendizService } from '../../services/aprendiz.service';
import { ShowNotificacionService } from '../../../core/service/show-notificacion.service';
import { RespuestasConocimientosPreviosComponent } from '../../components/respuestas-conocimientos-previos/respuestas-conocimientos-previos.component';
import { RespuestasTeoricoComponent } from '../../components/respuestas-teorico/respuestas-teorico.component';
import { BotonSalirComponent } from '../../components/boton-salir/boton-salir.component';
import { ShowAprendizComponent } from '../../components/show-aprendiz/show-aprendiz.component';
import { NgIf } from '@angular/common';
import { MatCardModule } from '@angular/material/card';

@Component({
    templateUrl: './finish-quiz.component.html',
    styleUrls: ['./finish-quiz.component.scss'],
    imports: [MatCardModule, NgIf, ShowAprendizComponent, BotonSalirComponent, RespuestasTeoricoComponent, RespuestasConocimientosPreviosComponent]
})
export class FinishQuizComponent implements OnInit {
  tipoEvaluacion = '';

  public opciones: Record<string, boolean> = {
    evaluacionTeorico: false,
    conocimientosPrevios: false,
    satisfaccionCliente: false,
  };


  constructor(
    private aprendizService: AprendizService,
    public router: Router,
    private route: ActivatedRoute,
    private messageService: MessageService,
    private notificacionService: ShowNotificacionService) {
      this.route.params.subscribe(params => {
        this.tipoEvaluacion = params['tipoevaluacion'];
        if (this.tipoEvaluacion == 'CONOCIMIENTOSTEORICOS') {
          this.opciones.evaluacionTeorico = true;
        }

        if (this.tipoEvaluacion == 'INGRESO') {
          this.opciones.conocimientosPrevios = true;
        }
      });

      this.search();
  }

  ngOnInit(): void {
    this.aprendizService.dataReadySubject.subscribe((dataReady: boolean) => {
      if (dataReady) {
        if (this.tipoEvaluacion === 'INGRESO') {
          this.updateSidebarText('Evaluación conocimientos previos')
        } else {
//          sessionStorage.removeItem('numerodocumento');
          this.updateSidebarText('Evaluación de conocimientos teóricos');
        }
      }
    });
  }

  async search() {
    const numerodocumento = sessionStorage.getItem('numerodocumento') || ''

    if (numerodocumento === null || numerodocumento === undefined || numerodocumento.length == 0) {
      alert("No existe numero de documento")
      if (this.tipoEvaluacion !== 'INGRESO') {
        this.router.navigate(['/quiz/inicio-teorico']);
      }
      return;
    }

    try {
      let tipoEvaluacion;

      if (this.opciones.conocimientosPrevios) {
        tipoEvaluacion = 'conocimientosprevios';
      }

      if (this.opciones.evaluacionTeorico) {
        tipoEvaluacion = 'teorico';
      }

      if (this.opciones.satisfaccionCliente) {
        tipoEvaluacion = 'satisfaccioncliente';
      }

      this.aprendizService.searchAprendiz(numerodocumento);

      sessionStorage.removeItem('numerodocumento')
    } catch (error) {
      this.notificacionService.displayError(error);
    }
  }

  get aprendiz(): Aprendiz {
    return this.aprendizService.aprendiz;
  }

  isEmpty(obj: any): boolean {
    return Object.keys(obj).length === 0;
  }

  updateSidebarText(message: string) {
    this.messageService.updateMessage(message);
  }
}
