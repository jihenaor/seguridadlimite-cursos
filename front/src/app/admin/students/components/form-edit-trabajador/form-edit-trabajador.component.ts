import { Component, inject, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormsModule, ReactiveFormsModule } from '@angular/forms';

import { Aprendiz } from 'src/app/core/models/aprendiz.model';
import { Trabajador } from 'src/app/core/models/trabajador.model';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { NgxMaskDirective } from 'ngx-mask';
import { MatOptionModule } from '@angular/material/core';
import { MatSelectModule } from '@angular/material/select';
import { NgIf, NgFor } from '@angular/common';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { TrabajadorService } from '../../../../core/service/trabajador.service';
import { ShowNotificacionService } from '../../../../core/service/show-notificacion.service';

@Component({
    selector: 'trabajador-form-edit-trabajador',
    templateUrl: './form-edit-trabajador.component.html',
    imports: [
        FormsModule,
        ReactiveFormsModule,
        MatFormFieldModule,
        MatInputModule,
        NgIf,
        MatSelectModule,
        MatOptionModule,
        NgxMaskDirective,
        MatIconModule,
        MatButtonModule
    ]
})
export class FormEditTrabajadorComponent implements OnInit {
  private fb = inject(FormBuilder);
  private trabajadorService = inject(TrabajadorService);
  private notificationService = inject(ShowNotificacionService);

  @Input()
  aprendiz: Aprendiz;

  public trabajador: Trabajador;

  public firstFormGroup: FormGroup;

  constructor(

) { }

  ngOnInit(): void {
    this.trabajador = this.aprendiz.trabajador;

    this.buildFormTrabajador();
  }

  updateTrabajador() {
    if (this.firstFormGroup.valid) {
      const trabajadorData = this.firstFormGroup.value;
      const trabajadorId = trabajadorData.id;

      this.trabajadorService.updateTrabajador(trabajadorId, trabajadorData).subscribe({
        next: () => {
          this.notificationService.displaySuccess('Trabajador actualizado exitosamente');
        },
        error: (error) => {
          this.notificationService.displayError(error?.message || 'Error al actualizar el trabajador');
        }
      });
    } else {
      this.notificationService.displayError('Por favor, complete todos los campos requeridos');
    }
  }


  private buildFormTrabajador() {
    this.firstFormGroup = this.fb.group({
      id: [this.trabajador.id],
      tipodocumento: [this.trabajador.tipodocumento, [Validators.required, Validators.pattern('[a-zA-Z]+')]],
      numerodocumento: [this.trabajador.numerodocumento, [Validators.required, Validators.pattern('[0-9]+'), , Validators.maxLength(16)]],
      primernombre: [this.trabajador.primernombre, [Validators.required, Validators.pattern('[a-zA-ZÑÁÉÍÓÚ]+'), , Validators.maxLength(20)]],
      segundonombre: [this.trabajador.segundonombre],
      primerapellido: [this.trabajador.primerapellido, [Validators.required, Validators.pattern('[a-zA-ZÑÁÉÍÓÚ]+'), , Validators.maxLength(20)]],
      segundoapellido: [this.trabajador.segundoapellido],
      genero: [this.trabajador.genero],
      fechanacimiento: [this.trabajador.fechanacimiento, [Validators.required , Validators.maxLength(10)]],
      tiposangre: [this.trabajador.tiposangre],
      celular: [this.trabajador.celular],
      correoelectronico: [
        this.trabajador.correoelectronico,
        [Validators.email, Validators.minLength(5), Validators.maxLength(40)]
      ],
      idgrupo: [this.trabajador.idgrupo],
      ocupacion: [this.trabajador.ocupacion],
      adjuntodocumento: [this.trabajador.adjuntodocumento],
      ext: [this.trabajador.ext],
      fechaemision: [this.trabajador.fechaemision],
      fechareentrenamiento: [this.trabajador.fechareentrenamiento],
      ciudadexpedicion: [this.trabajador.ciudadexpedicion],
      ciudadreentrenamiento: [this.trabajador.ciudadreentrenamiento],
      codigoverificacion: [this.trabajador.codigoverificacion]
    });
  }
}
