import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdminRoutingModule } from './admin-routing.module';
import { FormsModule } from '@angular/forms';

import { EmpresasService } from './empresas/all-empresas/empresas.service';
import { EpsService } from './eps/all-eps/eps.service';

import { PreguntasService } from './preguntas/all-preguntas/preguntas.service';
import { ProfessorsService } from './professors/all-professors/professors.service';
import { ProgramsService } from './programs/all-programs/programs.service';
import { EvaluacionteoricaService } from './students/evaluacionteorica/evaluacionteorica.service';
import { EvaluacionpracticaService } from './students/evaluacionpractica/evaluacionpractica.service';
import { WebcamModule } from 'ngx-webcam';

import { PagopendienteempresaService } from './empresas/pagospendientes/pagospendientes.service';
import { NivelsService } from '../core/service/nivels.service';
import { EnfasisService } from './enfasis/enfasis.service';

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    AdminRoutingModule,
    FormsModule,
    WebcamModule,
  ],
  providers: [
    EpsService,
    EnfasisService,
    EvaluacionteoricaService,
    EvaluacionpracticaService,
    ProgramsService,
    NivelsService,
    PreguntasService,
    ProfessorsService,
    ProfessorsService,
    EmpresasService,
    PagopendienteempresaService
  ]
})
export class AdminModule {}
