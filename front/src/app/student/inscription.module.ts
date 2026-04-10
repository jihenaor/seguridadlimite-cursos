import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { InfoComponent } from './info/info.component';
import { CheckqrComponent  } from './checkqr/checkqr.component';

import { MatIconModule } from '@angular/material/icon';
import { WebcamModule } from 'ngx-webcam';
import { NgxMaskDirective, NgxMaskPipe, provideNgxMask } from 'ngx-mask';
import { MatDatepickerModule } from '@angular/material/datepicker';

import { MatButtonToggleModule } from '@angular/material/button-toggle';

import { TerminosCondicionesComponent } from './inscription/terminos-condiciones/terminos-condiciones.component';
import { InscriptionComponent } from './inscription/inscription.component';

import { NumericOnlyDirective } from '../directives/numeric-only.directive';
import { NombreValidatorDirective } from '../directives/nombreValidator.directive';
import { ValidarFechaDirective } from '../directives/validar-fecha.directive';
import { FormSearchComponent } from './components/form-search/form-search.component';
import { ProgramaComponent } from './inscription/programa/programa.component';
import { InscriptionRoutingModule } from './inscription-routing.module';
import { Header2Component } from './components/header2/header2.component';
import { CertificateComponent } from './certificate/certificate.component';
import { SharedModule } from '../shared/shared.module';
import { GruposAsociadosComponent } from './inscription/gruposAsociados/grupos-asociados.component';

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        InscriptionRoutingModule,
        MatIconModule,
        WebcamModule,
        MatDatepickerModule,
        MatButtonToggleModule,
        NgxMaskDirective,
        NgxMaskPipe,
        SharedModule,
        InfoComponent,
        CheckqrComponent,
        Header2Component,
        FormSearchComponent,
        InscriptionComponent,
        CertificateComponent,
        TerminosCondicionesComponent,
        GruposAsociadosComponent,
        NumericOnlyDirective,
        NombreValidatorDirective,
        ValidarFechaDirective,
        FormSearchComponent,
        ProgramaComponent,
    ],
    providers: [provideNgxMask()],
})
export class InscriptionModule {}
