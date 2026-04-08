import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { UntypedFormBuilder, UntypedFormGroup, Validators, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ServicesService } from 'src/app/core/service/services.service';
import { MatButtonModule } from '@angular/material/button';
import { MatOptionModule } from '@angular/material/core';
import { MatSelectModule } from '@angular/material/select';
import { NgxMaskDirective } from 'ngx-mask';
import { NgIf } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { Header2Component } from '../components/header2/header2.component';
@Component({
    selector: 'app-info',
    templateUrl: './info.component.html',
    imports: [
        FormsModule,
        ReactiveFormsModule,
        MatFormFieldModule,
        MatInputModule,
        MatIconModule,
        NgIf,
        NgxMaskDirective,
        MatSelectModule,
        MatOptionModule,
        MatButtonModule,
        Header2Component
    ]
})
export class InfoComponent implements OnInit {
  register: UntypedFormGroup;
  constructor(
    private formBuilder: UntypedFormBuilder,
    private route: ActivatedRoute,
    private services: ServicesService,
    private router: Router
  ) {}

  ngOnInit() {
    this.craerFormGrupos();
  }

  craerFormGrupos() {
    this.register = this.formBuilder.group({
        numerodocumento: ['', [Validators.required]],
        nombrecontacto: ['', [Validators.required]],
        email: [
          '',
          [Validators.required, Validators.email, Validators.minLength(5)],
        ],
        celular: [''],
        horario: [''],
        cupo: [''],
        programa: [''],
        nivel: [''],
        empresa: [''],
        termcondition: [false, [Validators.requiredTrue]],
      });
  }

  async onRegister() {

    try {
      const resp = await this.services.post('/saveSolicitud', this.register.value);

      alert('El registro fué exitoso');
      window.location.href = "https://seguridadallimite.com";
    } catch (error) {
      alert('Error registrando los datos ' + error);
    }
  }
/*
  if (!this.mostrarformulario) {
    this.register.controls["numerodocumento"].setValue(numerodocumento);
  }
*/
}
