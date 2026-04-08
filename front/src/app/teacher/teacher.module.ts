import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { MatStepperModule } from '@angular/material/stepper';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatButtonToggleModule } from '@angular/material/button-toggle';
import { MatIconModule } from '@angular/material/icon';
import { MatNativeDateModule } from '@angular/material/core';
import { MatTableModule } from '@angular/material/table';

import { TeacherRoutingModule } from './teacher-routing.module';

import { SideBarComponent } from './components/sidebar/sidebar.component';
import { HomeTeacherComponent } from './pages/home-teacher/home-teacher.component';
import { EvaluarComponent } from './pages/evaluar/evaluar.component';
import { GruposComponent } from './components/grupos/grupos.component';
import { AprendicesGrupoComponent } from './components/aprendices-grupo/aprendices-grupo.component';
import { EvaluacionAprendizComponent } from './components/evaluacion-aprendiz/evaluacion-aprendiz.component';
import { AsistenciaGenerarReporteComponent } from './components/asistencia-generar-reporte/asistencia-generar-reporte.component';
import { UpdateInscriptionDateComponent } from './components/update-inscription-date/update-inscription-date.component';
import { SharedModule } from '../shared/shared.module';

import { STEPPER_GLOBAL_OPTIONS } from '@angular/cdk/stepper';
import { DeviceDetectorService } from '../core/service/device-detector.service';

@NgModule({
    imports: [
        CommonModule,
        TeacherRoutingModule,
        MatStepperModule,
        FormsModule,
        ReactiveFormsModule,
        MatIconModule,
        MatExpansionModule,
        MatDatepickerModule,
        MatNativeDateModule,
        MatButtonToggleModule,
        MatTableModule,
        SharedModule,
        // Importa componentes standalone directamente aquí
        SideBarComponent,
        HomeTeacherComponent,
        EvaluarComponent,
        GruposComponent,
        AprendicesGrupoComponent,
        EvaluacionAprendizComponent,
        AsistenciaGenerarReporteComponent,
        UpdateInscriptionDateComponent, // Este también es standalone
    ],
    providers: [
        {
            provide: STEPPER_GLOBAL_OPTIONS,
            useValue: { showError: true },
        },
        DeviceDetectorService,
    ],
})
export class TeacherModule { }
