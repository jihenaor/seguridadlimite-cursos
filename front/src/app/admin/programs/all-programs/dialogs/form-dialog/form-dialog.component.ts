import { Component, Inject } from '@angular/core';
import { ProgramsService } from '../../programs.service';
import { Programa } from '../../../../../core/models/programa.model';
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
  proForm: UntypedFormGroup;
  professors: Programa;
  constructor(
    public dialogRef: MatDialogRef<FormDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    public programService: ProgramsService,
    private fb: UntypedFormBuilder
  ) {
    // Set the defaults
    this.action = data.action;
    if (this.action === 'edit') {
      this.dialogTitle = data.professors.nombrecompleto;
      this.professors = data.professors;
    } else {
      this.dialogTitle = 'Nuevo programa';
      this.professors = new Programa({});
    }
    this.proForm = this.createContactForm();
  }
  formControl = new UntypedFormControl('', [
    Validators.required
    // Validators.email,
  ]);
  getErrorMessage() {
    return this.formControl.hasError('required')
      ? 'Required field'
      : this.formControl.hasError('email')
      ? 'Not a valid email'
      : '';
  }
  createContactForm(): UntypedFormGroup {
    return this.fb.group({
      id: [this.professors.id],
      nombre: [this.professors.nombre],
    });
  }
  submit() {
    // emppty stuff
  }
  onNoClick(): void {
    this.dialogRef.close();
  }
  public confirmAdd(): void {
    this.programService.addPrograma(this.proForm.getRawValue());
  }
}
