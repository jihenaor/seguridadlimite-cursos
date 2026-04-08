import { Component, OnInit, EventEmitter, Output, Input } from '@angular/core';
import { UntypedFormBuilder, UntypedFormGroup, Validators, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ServicesService } from '../../../core/service/services.service';
import { Trabajador } from '../../../core/models/trabajador.model';
import { RouterLink } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { NumericOnlyDirective } from '../../../directives/numeric-only.directive';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { NgIf } from '@angular/common';
import { MatCardModule } from '@angular/material/card';

@Component({
    selector: 'inscripcion-form-search',
    templateUrl: './form-search.component.html',
    standalone: true,
    imports: [MatCardModule, FormsModule, ReactiveFormsModule, NgIf, MatFormFieldModule, MatInputModule, MatIconModule, NumericOnlyDirective, MatButtonModule, RouterLink]
})
export class FormSearchComponent implements OnInit {
  authFormTrabajador: UntypedFormGroup;
  error: string = '';
  loading: boolean = false;
  @Output() trabajadorEncontrado = new EventEmitter<Trabajador>();

  constructor(private formBuilder: UntypedFormBuilder,
    private services: ServicesService,) {
    const empresa = sessionStorage.getItem('empresainscripcion') || '';
    const nit = sessionStorage.getItem('nit') || '';
    const nombrerepresentantelegal = sessionStorage.getItem('nombrerepresentantelegal') || '';

    this.authFormTrabajador = this.formBuilder.group({
      nombreempresa: [empresa],
      nit: [nit],
      nombrerepresentantelegal: [nombrerepresentantelegal],
      username: ['', Validators.required]
    });

  }

  ngOnInit(): void {
    this.authFormTrabajador.controls['username'].setValue(sessionStorage.getItem("numerodocumento") || '');
  }

  async validarTrabajador() {
    const numerodocumento = this.authFormTrabajador.get("username")?.value;
    sessionStorage.setItem("numerodocumento", numerodocumento);
    if (this.authFormTrabajador.get("nombreempresa")?.value.length > 0) {
      sessionStorage.setItem('empresainscripcion', this.authFormTrabajador.get("nombreempresa")?.value);
    }

    if (this.authFormTrabajador.invalid) {
      this.error = 'Debe digitar el numero de documento !';
      alert(this.error);
      return;
    }

    if (numerodocumento === undefined || numerodocumento === null || numerodocumento.length === 0) {
      alert('Seleccione un número de documento');
      return;
    }

    const trabajador = await this.services.executeFetch('/trabajadorinscripcion/' + numerodocumento);

    if (trabajador) {
      if (trabajador.numerodocumento == null || trabajador.numerodocumento.length == 0) {
        trabajador.tipodocumento = 'CC';
        trabajador.numerodocumento = numerodocumento;
      }

      if (trabajador.empresa == null || trabajador.empresa.length == 0) {
        trabajador.empresa = this.authFormTrabajador.get("nombreempresa")?.value.toUpperCase();
      }
    }
    this.trabajadorEncontrado.emit(trabajador);
  }

}
