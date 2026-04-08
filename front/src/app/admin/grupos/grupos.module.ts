import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatBadgeModule } from '@angular/material/badge';
import { MatBottomSheetModule } from '@angular/material/bottom-sheet';
import { MatButtonToggleModule } from '@angular/material/button-toggle';
import { MatRippleModule } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatDividerModule } from '@angular/material/divider';
import { MatExpansionModule } from '@angular/material/expansion';

import { CdkTableModule } from '@angular/cdk/table';

import { MatGridListModule } from '@angular/material/grid-list';
import { MatIconModule } from '@angular/material/icon';

import { MatSidenavModule } from '@angular/material/sidenav';

import { MatSortModule } from '@angular/material/sort';
import { MatStepperModule } from '@angular/material/stepper';

import { MatToolbarModule } from '@angular/material/toolbar';
import { MatTreeModule } from '@angular/material/tree';

import { ComponentsModule } from './../../shared/components/components.module';

import { SharedModule } from '../../shared/shared.module';

import { MatTableModule } from '@angular/material/table';


@NgModule({
    imports: [
        CommonModule,
        ComponentsModule,
        FormsModule,
        ReactiveFormsModule.withConfig({ warnOnNgModelWithFormControl: 'never' }),
        MatBadgeModule,
        MatBottomSheetModule,
        MatButtonToggleModule,
        MatDatepickerModule,
        MatDividerModule,
        MatExpansionModule,
        MatTableModule,
        ReactiveFormsModule,
        MatGridListModule,
        MatIconModule,
        MatRippleModule,
        MatSidenavModule,
        MatIconModule,
        MatSortModule,
        MatStepperModule,
        MatToolbarModule,
        MatDatepickerModule,
        MatToolbarModule,
        MatTreeModule,
        CdkTableModule,
        SharedModule,
    ]
})
export class GruposModule {}
