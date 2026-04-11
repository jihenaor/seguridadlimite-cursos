import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { DeviceService } from './../../services/device.service';
import { AprendizEvaluacionService } from '../../services/aprendizevaluacion.service';

import { Aprendiz } from 'src/app/core/models/aprendiz.model';
import { BotonSalirComponent } from '../../components/boton-salir/boton-salir.component';
import { ButonIniciarEvaluacionComponent } from '../../components/buton-iniciar-evaluacion/buton-iniciar-evaluacion.component';
import { ShowAprendizComponent } from '../../components/show-aprendiz/show-aprendiz.component';
import { NgIf } from '@angular/common';
import { SearchAprendizComponent } from '../../components/search-aprendiz/search-aprendiz.component';
import { InfoQuizComponent } from '../../components/info-quiz/info-quiz.component';

@Component({
    selector: 'app-inicio-quiz',
    standalone: true,
    templateUrl: './inicio-quiz.component.html',
    styleUrls: ['./inicio-quiz.component.scss'],
    imports: [
        InfoQuizComponent,
        SearchAprendizComponent,
        NgIf,
        ShowAprendizComponent,
        ButonIniciarEvaluacionComponent,
        BotonSalirComponent,
    ],
})
export class InicioQuizComponent implements OnInit {
  @ViewChild('numerodocumento') numerodocumento!: ElementRef;
  deviceId: string;
  public puedePresentarEvaluacion: boolean = true;

  public opciones: Record<string, boolean> = {
    evaluacionTeorico: false,
    conocimientosPrevios: false,
    satisfaccionCliente: false,
  };

  private urlMappings: Record<string, string> = {
    evaluacionTeorico: 'inicio-teorico',
    conocimientosPrevios: 'inicio-conocimientos-previos',
    satisfaccionCliente: 'inicio-satisfaccion-cliente',
  };

  constructor(
    private deviceService: DeviceService,
    private aprendizService: AprendizEvaluacionService,
    private route: ActivatedRoute) {
    this.deviceId = this.deviceService.getDeviceId();

    const urlSegments = this.route.snapshot.url.map(segment => segment.path);

    for (const opcion in this.opciones) {
      if (urlSegments.includes(this.urlMappings[opcion])) {
        this.opciones[opcion] = true;
      }
    }
  }

  ngOnInit(): void {
    if (this.opciones.evaluacionTeorico) {
      this.puedePresentarEvaluacion = this.aprendiz && this.aprendiz.eteorica1 < 4
      && this.aprendiz.eteorica2 === 0

      this.aprendizService.dataReadySubjectAprendizEvaluacion.subscribe((dataReady: boolean) => {
        if (dataReady) {
          this.puedePresentarEvaluacion = this.aprendiz && this.aprendiz.eteorica1 < 4
          && this.aprendiz.eteorica2 === 0
        }
      });
    } else {
      this.puedePresentarEvaluacion = true;
    }
  }

  get aprendiz(): Aprendiz {
    return this.aprendizService.aprendiz;
  }

  isEmpty(obj: any): boolean {
    return Object.keys(obj).length === 0;
  }
}
