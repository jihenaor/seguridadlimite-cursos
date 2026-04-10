import { Component, OnInit, ViewChild } from '@angular/core';
import { RouterLink } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatStepper, MatStepperModule } from '@angular/material/stepper';
import { AprendizGrupoTeacherService } from '../../services/aprendizgrupoteacher.service';
import { PreguntasEvaluacionTeacherService } from '../../services/preguntasevaluacionteacher.service';
import { EvaluacionAprendizComponent } from '../../components/evaluacion-aprendiz/evaluacion-aprendiz.component';
import { AprendicesGrupoComponent } from '../../components/aprendices-grupo/aprendices-grupo.component';
import { GruposComponent } from '../../components/grupos/grupos.component';

@Component({
    templateUrl: './evaluar.component.html',
    imports: [
        RouterLink,
        MatButtonModule,
        MatIconModule,
        MatStepperModule,
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
