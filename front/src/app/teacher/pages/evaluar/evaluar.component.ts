import { Component, OnInit, ViewChild } from '@angular/core';
import { Grupo } from 'src/app/core/models/grupo.model';
import { AprendizGrupoTeacherService } from '../../services/aprendizgrupoteacher.service';
import { Aprendiz } from 'src/app/core/models/aprendiz.model';
import { MatStepper, MatStepperModule } from '@angular/material/stepper';
import { PreguntasEvaluacionTeacherService } from '../../services/preguntasevaluacionteacher.service';
import { EvaluacionAprendizComponent } from '../../components/evaluacion-aprendiz/evaluacion-aprendiz.component';
import { AprendicesGrupoComponent } from '../../components/aprendices-grupo/aprendices-grupo.component';
import { GruposComponent } from '../../components/grupos/grupos.component';
import { FotoModalComponent } from '../../components/foto-modal/foto-modal.component';

@Component({
    templateUrl: './evaluar.component.html',
    imports: [MatStepperModule,
        GruposComponent,
        AprendicesGrupoComponent,
        EvaluacionAprendizComponent
    ]
})
export class EvaluarComponent implements OnInit {
  public idprofesor: string;
  public idaprendiz: string;

  @ViewChild('stepper') stepper: MatStepper;

  constructor(private aprendizService: AprendizGrupoTeacherService,
              private preguntasEvaluacionService: PreguntasEvaluacionTeacherService) { }

  ngOnInit(): void {
    this.idprofesor = sessionStorage.getItem("loginid");
  }

  onGrupoSelected() {
    this.stepper.next();
  }

  onAprendizSelected() {
    this.stepper.next();
  }
}
