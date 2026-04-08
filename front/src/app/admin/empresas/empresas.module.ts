import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { NgxMaskDirective, NgxMaskPipe, provideNgxMask } from 'ngx-mask';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatBadgeModule } from '@angular/material/badge';
import { MatBottomSheetModule } from '@angular/material/bottom-sheet';
import { MatButtonToggleModule } from '@angular/material/button-toggle';

import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatExpansionModule } from '@angular/material/expansion';

import { MatGridListModule } from '@angular/material/grid-list';
import { MatIconModule } from '@angular/material/icon';

import { MatToolbarModule } from '@angular/material/toolbar';

import { ComponentsModule } from './../../shared/components/components.module';
import { EmpresasRoutingModule } from './empresas-routing.module';
import { AllEmpresasComponent } from './all-empresas/all-empresas.component';
import { FormDialogComponent } from './all-empresas/dialogs/form-dialog/form-dialog.component';
import { PagospendientesComponent } from './pagospendientes/pagospendientes.component';
import { SharedModule } from '../../shared/shared.module';

@NgModule({
    imports: [
        CommonModule,
        HttpClientModule,
        ComponentsModule,
        FormsModule,
        ReactiveFormsModule.withConfig({ warnOnNgModelWithFormControl: 'never' }),
        MatBadgeModule,
        MatBottomSheetModule,
        MatButtonToggleModule,
        MatDatepickerModule,
        MatExpansionModule,
        ReactiveFormsModule,
        MatGridListModule,
        MatIconModule,
        NgxMaskDirective,
        NgxMaskPipe,
        MatIconModule,
        MatToolbarModule,
        MatToolbarModule,
        EmpresasRoutingModule,
        SharedModule,
        AllEmpresasComponent,
        FormDialogComponent,
        PagospendientesComponent
    ],
    providers: [provideNgxMask()],
})
export class EmpresasModule {}
