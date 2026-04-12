import { Component, Inject } from '@angular/core';
import { ProfessorsService } from '../../professors.service';
import { Professors } from './../../../../../core/models/professors.model';

import { UntypedFormControl, Validators, UntypedFormGroup, UntypedFormBuilder, FormsModule, ReactiveFormsModule } from '@angular/forms';

import { MAT_DIALOG_DATA, MatDialogRef, MatDialogContent } from '@angular/material/dialog';
import { NgIf } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MyValidators } from 'src/app/utils/validators';
import { UiOutlinedFieldComponent } from 'src/app/shared/components/ui-outlined-field/ui-outlined-field.component';

@Component({
    selector: 'app-form-dialog',
    templateUrl: './form-dialog.component.html',
    imports: [
        MatButtonModule,
        MatIconModule,
        MatDialogContent,
        FormsModule,
        ReactiveFormsModule,
        NgIf,
        UiOutlinedFieldComponent,
    ]
})
export class FormDialogComponent {
  action: string;
  dialogTitle: string;
  proForm: UntypedFormGroup;
  professors: Professors;

  readonly docTipoOptions = [
    { value: 'CC', label: 'Cédula de ciudadanía' },
    { value: 'PA', label: 'Pasaporte' },
    { value: 'PE', label: 'Permiso especial de permanencia' },
  ];

  readonly siNoOptions = [
    { value: 'S', label: 'Sí' },
    { value: 'N', label: 'No' },
  ];

  readonly roleOptions = [
    { value: 'INSTRUCTOR', label: 'Instructor' },
    { value: 'COORDINADOR', label: 'Coordinador' },
    { value: 'ADMINISTRADOR', label: 'Administrador' },
  ];

  constructor(
    public dialogRef: MatDialogRef<FormDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { action: string; professors?: Professors },
    public professorsService: ProfessorsService,
    private fb: UntypedFormBuilder
  ) {
    this.action = data.action;
    if (this.action === 'edit' && data.professors) {
      this.dialogTitle = data.professors.nombrecompleto;
      this.professors = { ...data.professors };
    } else {
      this.dialogTitle = 'Nuevo personal';
      this.professors = new Professors();
    }
    this.proForm = this.createContactForm();
  }

  formControl = new UntypedFormControl('', [Validators.required]);

  getErrorMessage() {
    return this.formControl.hasError('required')
      ? 'Campo requerido'
      : this.formControl.hasError('email')
        ? 'Correo no válido'
        : '';
  }

  createContactForm(): UntypedFormGroup {
    const newPasswordValidators = this.action === 'add'
      ? [Validators.required, Validators.minLength(8), MyValidators.validPassword]
      : [MyValidators.validPassword];

    return this.fb.group({
      id: [this.professors.id],
      tipodocumento: [this.professors.tipodocumento || 'CC', Validators.required],
      numerodocumento: [this.professors.numerodocumento, Validators.required],
      nombrecompleto: [this.professors.nombrecompleto, Validators.required],
      entrenador: [this.professors.entrenador ?? 'N', Validators.required],
      supervisor: [this.professors.supervisor ?? 'N', Validators.required],
      personaapoyo: [this.professors.personaapoyo ?? 'N', Validators.required],
      numerocelular: [this.professors.numerocelular],
      email: [
        this.professors.email,
        [Validators.required, Validators.email, Validators.minLength(5)]
      ],
      role: [this.professors.role || 'INSTRUCTOR', Validators.required],
      newPassword: ['', newPasswordValidators],
      confirmNewPassword: [''],
    });
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  async confirmSave(): Promise<void> {
    if (!this.proForm.valid) {
      return;
    }
    const v = this.proForm.getRawValue();
    const np = (v.newPassword || '').trim();
    const cp = (v.confirmNewPassword || '').trim();

    if (this.action === 'add') {
      if (np.length < 8) {
        alert('La contraseña inicial debe tener al menos 8 caracteres e incluir un número.');
        return;
      }
      if (np !== cp) {
        alert('La contraseña y la confirmación no coinciden.');
        return;
      }
    } else {
      if (np || cp) {
        if ((np && !cp) || (!np && cp)) {
          alert('Complete ambos campos de contraseña para cambiarla.');
          return;
        }
        if (np.length < 8) {
          alert('La nueva contraseña debe tener al menos 8 caracteres e incluir un número.');
          return;
        }
        if (np !== cp) {
          alert('La contraseña y la confirmación no coinciden.');
          return;
        }
      }
    }

    const payload: Record<string, unknown> = {
      tipodocumento: v.tipodocumento,
      numerodocumento: v.numerodocumento,
      nombrecompleto: v.nombrecompleto,
      entrenador: v.entrenador,
      supervisor: v.supervisor,
      personaapoyo: v.personaapoyo,
      numerocelular: v.numerocelular,
      email: v.email,
      role: v.role,
    };

    if (np) {
      payload['newPassword'] = np;
    }

    try {
      if (this.action === 'edit') {
        payload['id'] = this.professors.id;
        const saved = await this.professorsService.updateProfessors(payload);
        if (!saved) {
          return;
        }
      } else {
        payload['aucodestad'] = 'A';
        payload['personaautoriza'] = 'N';
        payload['responsableemergencias'] = 'N';
        payload['coordinadoralturas'] = 'N';
        const saved = await this.professorsService.addProfessors(payload);
        if (!saved) {
          return;
        }
      }
      this.dialogRef.close(1);
    } catch {
      // errores de red ya mostrados en el servicio
    }
  }
}
