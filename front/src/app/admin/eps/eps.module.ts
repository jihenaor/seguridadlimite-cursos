import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatBottomSheetModule } from '@angular/material/bottom-sheet';
import { MatRippleModule } from '@angular/material/core';

import { MatDividerModule } from '@angular/material/divider';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatIconModule } from '@angular/material/icon';

import { MatSidenavModule } from '@angular/material/sidenav';

import { MatSortModule } from '@angular/material/sort';
import { MatStepperModule } from '@angular/material/stepper';

import { MatToolbarModule } from '@angular/material/toolbar';
import { MatTreeModule } from '@angular/material/tree';

import { ComponentsModule } from './../../shared/components/components.module';
import { EpsRoutingModule } from './eps-routing.module';
import { AllEpsComponent } from './all-eps/all-eps.component';
import { DeleteDialogComponent } from './all-eps/dialogs/delete/delete.component';
import { FormDialogComponent } from './all-eps/dialogs/form-dialog/form-dialog.component';
import { SharedModule } from '../../shared/shared.module';
import { MatDialogModule } from '@angular/material/dialog';


@NgModule({
    imports: [
        ComponentsModule,
        CommonModule,
        FormsModule,
        ReactiveFormsModule.withConfig({ warnOnNgModelWithFormControl: 'never' }),
        MatBottomSheetModule,
        MatDividerModule,
        MatDialogModule,
        MatExpansionModule,
        ReactiveFormsModule,
        MatGridListModule,
        MatIconModule,
        MatRippleModule,
        MatSidenavModule,
        MatIconModule,
        MatSortModule,
        MatStepperModule,
        MatToolbarModule,
        MatToolbarModule,
        MatTreeModule,
        EpsRoutingModule,
        SharedModule,
        AllEpsComponent,
        DeleteDialogComponent,
        FormDialogComponent
    ]
})
export class EpsModule {}
