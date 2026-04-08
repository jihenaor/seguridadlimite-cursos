import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { MatIconModule } from '@angular/material/icon';

import { MatSortModule } from '@angular/material/sort';
import { MatToolbarModule } from '@angular/material/toolbar';
import { ProgramsRoutingModule } from './programs-routing.module';
import { AllprogramsComponent } from './all-programs/all-programs.component';
import { FormDialogComponent } from './all-programs/dialogs/form-dialog/form-dialog.component';
import { ComponentsModule } from './../../shared/components/components.module';
import { SharedModule } from '../../shared/shared.module';

@NgModule({
    imports: [
        ComponentsModule,
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        MatIconModule,
        MatSortModule,
        MatToolbarModule,
        ProgramsRoutingModule,
        SharedModule,
        AllprogramsComponent,
        FormDialogComponent
    ]
})
export class ProgramsModule {}
