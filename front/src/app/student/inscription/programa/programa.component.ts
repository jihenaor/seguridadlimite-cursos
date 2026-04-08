import { Component } from '@angular/core';
import { UntypedFormControl, UntypedFormGroup, Validators, FormsModule, ReactiveFormsModule } from '@angular/forms';

import { InscripcionService } from '../../services/inscripcion.service';
import { MatOptionModule } from '@angular/material/core';
import { MatSelectModule } from '@angular/material/select';
import { MatFormFieldModule } from '@angular/material/form-field';

@Component({
    selector: 'app-programa',
    templateUrl: './programa.component.html',
    imports: [
        MatFormFieldModule,
        MatSelectModule,
        FormsModule,
        ReactiveFormsModule,
        MatOptionModule,
    ]
})
export class ProgramaComponent {
  constructor(private inscripcionService: InscripcionService) {

    const formGroup: UntypedFormGroup = this.inscripcionService.mainForm.get('componente1') as UntypedFormGroup;

    formGroup.addControl('idnivel', new UntypedFormControl('', Validators.required));
  }

}
