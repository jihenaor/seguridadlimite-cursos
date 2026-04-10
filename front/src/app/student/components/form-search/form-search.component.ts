import { Component, OnInit, EventEmitter, Output, Input } from '@angular/core';
import { UntypedFormBuilder, UntypedFormGroup, Validators, FormsModule, ReactiveFormsModule } from '@angular/forms';

import { Trabajador } from '../../../core/models/trabajador.model';
import { RouterLink } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { NumericOnlyDirective } from '../../../directives/numeric-only.directive';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { NgIf, NgClass } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { FindTrabajadorInscripcionNumeroDocumentoService } from '../../../core/service/findTrabajadorInscripcionNumeroDocumento.service';

@Component({
    selector: 'inscripcion-form-search',
    templateUrl: './form-search.component.html',
    styleUrl: './form-search.component.scss',
    imports: [
        MatCardModule,
        FormsModule,
        ReactiveFormsModule,
        NgClass,
        MatFormFieldModule,
        MatInputModule,
        MatIconModule,
        NumericOnlyDirective,
        MatButtonModule,
        RouterLink
    ]
})
export class FormSearchComponent implements OnInit {
  authFormTrabajador: UntypedFormGroup;
  error: string = '';
  loading: boolean = false;
  @Output() trabajadorEncontrado = new EventEmitter<Trabajador>();
  @Input() esEmpresa: boolean = false;

  constructor(private formBuilder: UntypedFormBuilder,
    private service: FindTrabajadorInscripcionNumeroDocumentoService,) {
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
    const numerodocumento = sessionStorage.getItem("numerodocumento") || '';
    this.authFormTrabajador.controls['username'].setValue(numerodocumento);
    
    if (numerodocumento && numerodocumento.trim().length > 0) {
      this.validarTrabajador();
    }
  }

  validarTrabajador() {
    const numerodocumento = this.authFormTrabajador.get("username")?.value;

    // Validar si el número de documento es válido
    if (!numerodocumento || numerodocumento.trim().length === 0) {
      this.error = 'Debe digitar el número de documento.';
      alert(this.error);
      return;
    }

    // Guardar número de documento en sessionStorage
    sessionStorage.setItem("numerodocumento", numerodocumento);

    // Guardar empresa en sessionStorage si está presente
    const nombreEmpresa = this.authFormTrabajador.get("nombreempresa")?.value;
    if (nombreEmpresa && nombreEmpresa.trim().length > 0) {
      sessionStorage.setItem("empresainscripcion", nombreEmpresa.trim());
    }

    // Validar formulario
    if (this.authFormTrabajador.invalid) {
      this.error = 'El formulario es inválido. Por favor verifique los datos.';
      alert(this.error);
      return;
    }

    // Llamar al servicio y manejar la respuesta con subscribe
    this.service.getTrabajadorInscripcion(numerodocumento).subscribe({
      next: (trabajador) => {
        // Procesar respuesta exitosa
        trabajador.tipodocumento = trabajador.tipodocumento || 'CC';
        trabajador.numerodocumento = trabajador.numerodocumento || numerodocumento;
        trabajador.empresa = trabajador.empresa || nombreEmpresa?.toUpperCase();

        this.trabajadorEncontrado.emit(trabajador);
      },
      error: (error) => {
        // Manejar errores
        if (error.message === 'Trabajador no encontrado') {
          alert('Trabajador no encontrado.');
        } else {
          console.error('Error al validar el trabajador:', error);
          alert('Ocurrió un error al validar el trabajador. Por favor intente nuevamente.');
        }
      },
    });
  }

}
