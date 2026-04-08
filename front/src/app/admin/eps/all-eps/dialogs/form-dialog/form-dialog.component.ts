import { Component, Inject } from '@angular/core';
import { EpsService } from '../../eps.service';
import { Eps} from '../../../../../core/models/eps.model';

import { UntypedFormControl, Validators, UntypedFormGroup, UntypedFormBuilder, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef, MatDialogContent, MatDialogClose } from '@angular/material/dialog';
import { NgIf } from '@angular/common';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
@Component({
    selector: 'app-form-dialog',
    templateUrl: './form-dialog.component.html',
    styleUrls: ['./form-dialog.component.sass'],
    imports: [MatButtonModule, MatIconModule, MatDialogContent, FormsModule, ReactiveFormsModule, MatFormFieldModule, MatInputModule, NgIf, MatDialogClose]
})
export class FormDialogComponent {
  action: string;
  dialogTitle: string;
  stdForm: UntypedFormGroup;
  eps: Eps;

  ideps: number;
  constructor(
    public dialogRef: MatDialogRef<FormDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    public gruposService: EpsService,
    private fb: UntypedFormBuilder
  ) {
    // Set the defaults
    this.action = data.action;
    this.ideps= data.ideps;

    if (this.action === 'edit') {
      this.dialogTitle = 'Edición de la eps';
      this.eps= data.curso;
    } else {
      this.dialogTitle = 'Nueva eps';

      this.eps = new Eps({
        ideps: this.ideps,
      });
    }
    this.stdForm = this.createForm();
  }
  formControl = new UntypedFormControl('', [
    Validators.required
    // Validators.email,
  ]);
  getErrorMessage() {
    return this.formControl.hasError('required')
      ? 'Required field'
      : this.formControl.hasError('nombre')
      ? 'Not a valid nombre'
      : '';
  }
  createForm(): UntypedFormGroup {
    return this.fb.group({
      id: [this.eps.id],
      nombre: [this.eps.nombre],
    });
  }
  submit() {
    // emppty stuff
  }
  onNoClick(): void {
    this.dialogRef.close();
  }

  async confirmAdd() {
    const res = this.gruposService.addEmpresa(this.stdForm.getRawValue());
  }
}
