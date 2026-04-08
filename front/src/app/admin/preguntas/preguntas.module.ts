import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { MatIconModule } from '@angular/material/icon';
import { MatSortModule } from '@angular/material/sort';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { PreguntasRoutingModule } from './preguntas-routing.module';
import { AllpreguntasComponent } from './all-preguntas/all-preguntas.component';
import { FormDialogComponent } from './all-preguntas/dialogs/form-dialog/form-dialog.component';

import { RespuestasComponent } from './components/respuestas/respuestas.component';
import { SharedModule } from '../../shared/shared.module';
@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        MatIconModule,
        MatSortModule,
        MatToolbarModule,
        MatDatepickerModule,
        PreguntasRoutingModule,
        SharedModule,
        AllpreguntasComponent,
        FormDialogComponent,
        RespuestasComponent
    ]
})
export class PreguntasModule {}
