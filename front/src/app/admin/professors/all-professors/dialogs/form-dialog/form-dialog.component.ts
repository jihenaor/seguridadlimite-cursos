import { Component, Inject } from '@angular/core';
import { ProfessorsService } from '../../professors.service';
import { Professors } from './../../../../../core/models/professors.model';

import { UntypedFormControl, Validators, UntypedFormGroup, UntypedFormBuilder, FormsModule, ReactiveFormsModule } from '@angular/forms';

import { MAT_DIALOG_DATA, MatDialogRef, MatDialogContent, MatDialogClose } from '@angular/material/dialog';
import { MatInputModule } from '@angular/material/input';
import { NgIf } from '@angular/common';
import { MatOptionModule } from '@angular/material/core';
import { MatSelectModule } from '@angular/material/select';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
@Component({
    selector: 'app-form-dialog',
    templateUrl: './form-dialog.component.html',
    styleUrls: ['./form-dialog.component.sass'],
    imports: [MatButtonModule, MatIconModule, MatDialogContent, FormsModule, ReactiveFormsModule, MatFormFieldModule, MatSelectModule, MatOptionModule, NgIf, MatInputModule, MatDialogClose]
})
export class FormDialogComponent {
  action: string;
  dialogTitle: string;
  proForm: UntypedFormGroup;
  professors: Professors;
  constructor(
    public dialogRef: MatDialogRef<FormDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    public professorsService: ProfessorsService,
    private fb: UntypedFormBuilder
  ) {
    // Set the defaults
    this.action = data.action;
    if (this.action === 'edit') {
      this.dialogTitle = data.professors.nombrecompleto;
      this.professors = data.professors;
    } else {
      this.dialogTitle = 'Nuevo entrenador';
      this.professors = new Professors();
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
      img: [this.professors.img],
      tipodocumento: [this.professors.tipodocumento],
      numerodocumento: [this.professors.numerodocumento],
      nombrecompleto: [this.professors.nombrecompleto],
      entrenador: [this.professors.numerocelular],
      supervisor: [this.professors.numerocelular],
      numerocelular: [this.professors.numerocelular],
      email: [
        this.professors.email,
        [Validators.required, Validators.email, Validators.minLength(5)]
      ],
    });
  }
  submit() {
    // emppty stuff
  }
  onNoClick(): void {
    this.dialogRef.close();
  }
  public confirmAdd(): void {
    this.professorsService.addProfessors(this.proForm.getRawValue());
  }
}
