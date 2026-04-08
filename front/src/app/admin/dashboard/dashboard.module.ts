import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DashboardRoutingModule } from './dashboard-routing.module';
import { MainComponent } from './main/main.component';
import { MatIconModule } from '@angular/material/icon';


import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { CambioclaveComponent } from './cambioclave/cambioclave.component';
import { SharedModule } from '../../shared/shared.module';

@NgModule({
    imports: [
        CommonModule,
        DashboardRoutingModule,
        MatIconModule,
        FormsModule,
        ReactiveFormsModule,
        SharedModule,
        MainComponent,
        CambioclaveComponent
    ],
})
export class DashboardModule {}
