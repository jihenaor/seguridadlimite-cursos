
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonToggleModule } from '@angular/material/button-toggle';
import { HomeQuizComponent } from './pages/home/home-quiz.component';
import {MatDividerModule} from '@angular/material/divider';

import { InicioQuizComponent } from './pages/inicio-quiz/inicio-quiz.component';
import { DoQuizComponent } from './pages/do-quiz/do-quiz.component'
import { FinishQuizComponent } from './pages/finish-quiz/finish-quiz.component';

import { PreguntaaprendizService } from './services/preguntaaprendiz.service';
import { InfoQuizComponent } from './components/info-quiz/info-quiz.component';
import { QuizRoutingModule } from './quiz-routing.module';
import { SidebarComponent } from './components/sidebar/sidebar.component';

import { SearchAprendizComponent } from './components/search-aprendiz/search-aprendiz.component';
import { ShowAprendizComponent } from './components/show-aprendiz/show-aprendiz.component';

import { ChangeBgDirective } from './change-bg.directive';
import { DeviceService } from './services/device.service';
import { SaveevaluacionService } from './services/saveevaluacion.service';
import { ButonIniciarEvaluacionComponent } from './components/buton-iniciar-evaluacion/buton-iniciar-evaluacion.component';
import { BotonSalirComponent } from './components/boton-salir/boton-salir.component';
import { InfoConocimientospreviosComponent } from './components/info-conocimientosprevios/info-conocimientosprevios.component';
import { SatisfaccionclienteComponent } from './pages/satisfaccioncliente/satisfaccioncliente.component';
import { RespuestasTeoricoComponent } from './components/respuestas-teorico/respuestas-teorico.component';
import { RespuestasConocimientosPreviosComponent } from './components/respuestas-conocimientos-previos/respuestas-conocimientos-previos.component';
import { SharedModule } from '../shared/shared.module';

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        MatToolbarModule,
        MatDividerModule,
        MatIconModule,
        MatButtonToggleModule,
        QuizRoutingModule,
        ReactiveFormsModule,
        SharedModule,
        SidebarComponent,
        ShowAprendizComponent,
        HomeQuizComponent,
        InfoQuizComponent,
        InicioQuizComponent,
        DoQuizComponent,
        ChangeBgDirective,
        FinishQuizComponent,
        ButonIniciarEvaluacionComponent,
        BotonSalirComponent,
        SearchAprendizComponent,
        InfoConocimientospreviosComponent,
        SatisfaccionclienteComponent,
        RespuestasTeoricoComponent,
        RespuestasConocimientosPreviosComponent
    ],
    exports: [],
    providers: [
        DeviceService,
        PreguntaaprendizService,
        SaveevaluacionService
    ],
})
export class QuizModule {}
