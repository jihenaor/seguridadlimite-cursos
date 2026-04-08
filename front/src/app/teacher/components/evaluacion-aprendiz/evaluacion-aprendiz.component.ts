import { Component, OnInit, ViewChild, OnDestroy } from '@angular/core';
import { ComunicacionService } from '../../services/comunicacion.service';
import { PreguntasEvaluacionTeacherService } from '../../services/preguntasevaluacionteacher.service';

import { Grupoevaluacion } from 'src/app/core/models/grupoevaluacion.model';
import { MatAccordion, MatExpansionModule } from '@angular/material/expansion';
import { Evaluacion } from 'src/app/core/models/evaluacion.model';
import { Aprendiz } from 'src/app/core/models/aprendiz.model';

import { Subscription, tap } from 'rxjs';
import { SaveevaluacionteacherService } from '../../services/saveevaluacionteacher.service';
import { DeviceDetectorService } from '../../../core/service/device-detector.service';
import { AprendizFindIdService } from '../../../core/service/aprendizfindid.service';
import { ShowNotificacionService } from '../../../core/service/show-notificacion.service';
import { MatButtonToggleModule } from '@angular/material/button-toggle';
import { MatIconModule } from '@angular/material/icon';
import { NgFor, NgClass } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';

@Component({
    selector: 'teacher-evaluacion-aprendiz',
    styleUrls: ['./evaluacion-aprendiz.component.scss'],
    templateUrl: './evaluacion-aprendiz.component.html',
    imports: [MatButtonModule, MatExpansionModule, NgFor, NgClass, MatIconModule, MatButtonToggleModule]
})
export class EvaluacionAprendizComponent implements OnInit, OnDestroy {
  @ViewChild(MatAccordion) accordion: MatAccordion;

  cont: number = 0;
  public grupoevaluacions: Grupoevaluacion[] = [];
  public aprendiz: Aprendiz;
  private dataReadySubscription: Subscription;

  breadscrums = [
    {
      title: 'Basic',
      items: ['Tables'],
      active: 'Basic',
    },
  ];

  constructor(
    private comunicacionService: ComunicacionService,
    private preguntasEvaluacionService: PreguntasEvaluacionTeacherService,
    private saveevaluacionteacherService: SaveevaluacionteacherService,
    private deviceDetectorService: DeviceDetectorService,
    private aprendizFindIdService: AprendizFindIdService,
    private notificacionService: ShowNotificacionService
    ) { }

  ngOnInit(): void {
    this.comunicacionService.aprendizCambiado$.subscribe(aprendiz => {
      this.aprendiz = aprendiz;
      this.getEvaluacion(aprendiz.id + '')
    });

    this.dataReadySubscription = this.aprendizFindIdService.dataReadySubject.subscribe(
      (dataReady: boolean) => {
        if (dataReady) {
          this.aprendiz = this.aprendizFindIdService.aprendiz;
        }
      }
    );

    this.preguntasEvaluacionService.dataReadySubject.subscribe((dataReady: boolean) => {
      if (dataReady) {
        this.grupoevaluacions = this.preguntasEvaluacionService.grupoevaluacion;
      }
    });
  }

  getEvaluacion(idaprendiz: string) {
    this.preguntasEvaluacionService.searchPreguntasaprendiz(idaprendiz);
  }

  onFontStyleChange(value: string, evaluacion: Evaluacion) {
    evaluacion.respuestacorrecta = value;
  }

  guardarEvaluacion(): void {
    this.saveevaluacionteacherService.saveEvaluacion(this.grupoevaluacions)
      .pipe(
        tap(response => {
          this.notificacionService.displaySuccess('Actualización exitosa');

          this.consultarAprendiz();
        })
      )
      .subscribe();
  }

  getButtonStyle(evaluacion: Evaluacion, opcion: string): string {
    if (opcion === 'N') {
      return evaluacion.respuestacorrecta === 'N' ? 'red-button' : 'gray-button';
    }
    if (opcion === 'S') {
      return evaluacion.respuestacorrecta === 'S' ? 'green-button' : 'gray-button';
    }
    if (opcion === 'A') {
      return evaluacion.respuestacorrecta === 'A' ? 'blue-button' : 'gray-button';
    }
  }

  isSmallDevice(): boolean {
    return this.deviceDetectorService.isSmallDevice();
  }

  consultarAprendiz() {
    this.aprendizFindIdService.searchAprendizId(this.aprendiz.id);
  }

  ngOnDestroy(): void {
    this.dataReadySubscription.unsubscribe();
  }
}
