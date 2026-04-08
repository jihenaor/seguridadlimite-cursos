import { Component, Inject, OnInit } from '@angular/core';
import { UntypedFormControl, Validators, UntypedFormGroup, UntypedFormBuilder, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef, MatDialogClose } from '@angular/material/dialog';

import { EmpresasService } from '../../empresas.service';

import { Empresa } from '../../../../../core/models/empresa.model';
import { Professors } from '../../../../../core/models/professors.model';
import { MatInputModule } from '@angular/material/input';

import { MatOptionModule } from '@angular/material/core';
import { MatSelectModule } from '@angular/material/select';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { EnfasisService } from '../../../../enfasis/enfasis.service';

@Component({
    selector: 'app-form-dialog',
    templateUrl: './form-dialog.component.html',
    styleUrls: ['./form-dialog.component.scss'],
    imports: [
      MatButtonModule, 
      MatIconModule, 
      FormsModule, 
      ReactiveFormsModule, 
      MatFormFieldModule, 
      MatSelectModule, 
      MatOptionModule, 
      MatInputModule, 
      MatDialogClose,]
})
export class FormDialogComponent implements OnInit {
  action: string;
  dialogTitle: string;
  stdForm: UntypedFormGroup;
  empresa: Empresa;
  personals: Professors;
  idcurso: number;
  constructor(
    public dialogRef: MatDialogRef<FormDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    public gruposService: EmpresasService,
    public enfasisService: EnfasisService,
    private fb: UntypedFormBuilder
  ) {
    // Set the defaults
    this.action = data.action;
    this.personals = data.personals;
    this.idcurso = data.idcurso;

    if (this.action === 'edit') {
      this.dialogTitle = 'Edición del empresa';
      this.empresa = data.empresa;
    } else {
      this.dialogTitle = 'Nuevo empresa';

      this.empresa = new Empresa({
        idcurso: this.idcurso,
      });
    }
    this.stdForm = this.createContactForm();
  }

  ngOnInit(): void {
    console.log('Loading enfasis in ngOnInit...');
    // Subscribe to data changes
    this.enfasisService.dataChange.subscribe(data => {
      console.log('Enfasis data updated:', {
        hasData: !!data,
        length: data?.length || 0,
        data: data
      });
    });
    
    // Initial load
    this.enfasisService.getAllEnfasis();
  }

  formControl = new UntypedFormControl('', [
    Validators.required
    // Validators.email,
  ]);
  getErrorMessage() {
    return this.formControl.hasError('required')
      ? 'Required field'
      : this.formControl.hasError('correoelectronico')
      ? 'Not a valid email'
      : '';
  }
  createContactForm(): UntypedFormGroup {
    const form = this.fb.group({
      id: [this.empresa.id],
      tipodocumento: [this.empresa.tipodocumento],
      numerodocumento: [this.empresa.numerodocumento],
      nombre: [this.empresa.nombre],
      direccion: [this.empresa.direccion],
      telefono: [this.empresa.telefono],
      nombrecontacto: [this.empresa.nombrecontacto],
      nombrerepresentantelegal: [this.empresa.nombrerepresentantelegal],
      telefonocontacto: [this.empresa.telefonocontacto],
      certificadosinpago: [this.empresa.certificadosinpago],
      idenfasis: ['', Validators.required],
      seleccion: [this.empresa.seleccion],
    });

    // Set idenfasis if editing
    if (this.action === 'edit' && this.empresa.idenfasis) {
      form.patchValue({ idenfasis: this.empresa.idenfasis });
    }

    return form;
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
