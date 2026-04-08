import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatBadgeModule } from '@angular/material/badge';
import { MatBottomSheetModule } from '@angular/material/bottom-sheet';
import { MatButtonToggleModule } from '@angular/material/button-toggle';
import { MatRippleModule } from '@angular/material/core';

import { MatDividerModule } from '@angular/material/divider';
import { MatExpansionModule } from '@angular/material/expansion';

import { MatGridListModule } from '@angular/material/grid-list';
import { MatIconModule } from '@angular/material/icon';

import { MatSidenavModule } from '@angular/material/sidenav';

import { MatToolbarModule } from '@angular/material/toolbar';

import { NivelsRoutingModule } from './nivels-routing.module';
import { AllNivelsComponent } from './all-nivels/all-nivels.component';
import { DisenocurricularComponent } from './disenocurricular/disenocurricular.component';
import { SharedModule } from '../../shared/shared.module';

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        ReactiveFormsModule.withConfig({ warnOnNgModelWithFormControl: 'never' }),
        MatBadgeModule,
        MatBottomSheetModule,
        MatButtonToggleModule,
        MatDividerModule,
        MatExpansionModule,
        ReactiveFormsModule,
        MatGridListModule,
        MatIconModule,
        MatRippleModule,
        MatSidenavModule,
        MatIconModule,
        MatToolbarModule,
        MatToolbarModule,
        NivelsRoutingModule,
        SharedModule,
        AllNivelsComponent,
        DisenocurricularComponent
    ]
})
export class NivelsModule {}
