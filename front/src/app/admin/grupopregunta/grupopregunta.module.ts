import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsRoutingModule } from './grupopregunta-routing.module';
import { MatIconModule } from '@angular/material/icon';

import { AllGrupopreguntaComponent } from './all-grupopregunta/all-grupopregunta.component';
import { SharedModule } from '../../shared/shared.module';
import { ComponentsModule } from './../../shared/components/components.module';

@NgModule({
    imports: [
        CommonModule,
        ComponentsModule,
        FormsRoutingModule,
        MatIconModule,
        SharedModule,
        AllGrupopreguntaComponent
    ]
})
export class GrupopreguntaModule { }
