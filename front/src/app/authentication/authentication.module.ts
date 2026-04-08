import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AuthenticationRoutingModule } from './authentication-routing.module';
import { Page500Component } from './page500/page500.component';
import { Page404Component } from './page404/page404.component';
import { SigninComponent } from './signin/signin.component';
import { MatCardModule } from '@angular/material/card';
import { MatAutocompleteModule } from '@angular/material/autocomplete';

import { SharedModule } from '../shared/shared.module';

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        AuthenticationRoutingModule,
        MatAutocompleteModule,
        SharedModule,
        Page500Component,
        Page404Component,
        SigninComponent
    ]
})
export class AuthenticationModule {}
