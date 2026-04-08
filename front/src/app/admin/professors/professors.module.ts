import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { MatIconModule } from '@angular/material/icon';
import { MatSortModule } from '@angular/material/sort';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { ProfessorsRoutingModule } from './professors-routing.module';
import { AllprofessorsComponent } from './all-professors/all-professors.component';
import { DeleteDialogComponent } from './all-professors/dialogs/delete/delete.component';
import { FormDialogComponent } from './all-professors/dialogs/form-dialog/form-dialog.component';
import { AboutProfessorComponent } from './about-professor/about-professor.component';
import { SharedModule } from 'src/app/shared/shared.module';

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        MatIconModule,
        MatSortModule,
        MatToolbarModule,
        MatDatepickerModule,
        ProfessorsRoutingModule,
        SharedModule,
        AllprofessorsComponent,
        DeleteDialogComponent,
        FormDialogComponent,
        AboutProfessorComponent
    ]
})
export class ProfessorsModule {}
