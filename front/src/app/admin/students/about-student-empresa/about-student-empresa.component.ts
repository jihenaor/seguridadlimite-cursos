import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { UntypedFormBuilder, UntypedFormGroup, Validators, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { DomSanitizer } from '@angular/platform-browser';

import { ServicesService } from '../../../core/service/services.service';
import { Trabajadordocumento } from '../../../core/models/trabajadordocumento.model';
import { Documento } from '../../../core/models/documento.model';
import { Fototrabajador } from '../../../core/models/fototrabajador.model';
import { Aprendiz } from '../../../core/models/aprendiz.model';
import { Departamento } from '../../../core/models/departamento.model';
import { Ciudad } from '../../../core/models/ciudad.model';
import { Eps } from '../../../core/models/eps.model';
import { Arl } from '../../../core/models/arl.model';
import { DepartamentService } from 'src/app/core/service/departamentos.service';
import { FileUploadComponent } from '../../../shared/components/file-upload/file-upload.component';
import { MatButtonModule } from '@angular/material/button';
import { NgxMaskDirective } from 'ngx-mask';
import { MatOptionModule } from '@angular/material/core';
import { MatSelectModule } from '@angular/material/select';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { NgIf, NgFor } from '@angular/common';

@Component({
    selector: 'app-about-student-empresa',
    templateUrl: './about-student-empresa.component.html',
    styleUrls: ['./about-student-empresa.component.sass'],
    imports: [NgIf, RouterLink, FormsModule, ReactiveFormsModule, MatFormFieldModule, MatInputModule, MatIconModule, MatSelectModule, MatOptionModule, NgFor, NgxMaskDirective, MatButtonModule, FileUploadComponent]
})
export class AboutStudentempresaComponent implements OnInit {
  public aprendiz: Aprendiz;
  public idaprendiz;
  public firstFormGroup: UntypedFormGroup;
  public aprendizFormGroup: UntypedFormGroup;
  public panelOpenState = false;
  public fototrabajador: Fototrabajador;
  public documento: Documento;
  public trabajadordocumento: Trabajadordocumento;
  public epss: Eps[];
  public arls: Arl[];
  public departamentos: Departamento[] = [];
  public ciudadesDomicilio: Ciudad[] = [];
  public invalid = [];



  @ViewChild("fileUploada", {static: false}) fileUploada: ElementRef;
  @ViewChild("fileUploadb", {static: false}) fileUploadb: ElementRef;

  @ViewChild("fileUploadaprendiz", {static: false}) fileUploadaprendiz: ElementRef;
  files  = [];

  constructor(private formBuilder: UntypedFormBuilder,
    public service: ServicesService,
    private departamentosService: DepartamentService,
    private route: ActivatedRoute,
    private sanitizer: DomSanitizer) {
    this.route.params.subscribe(params => {
      this.idaprendiz = params['idaprendiz'];
    });
  }

  async ngOnInit() {
    this.departamentos = this.departamentosService.getDepartamentos();
    this.loadData();

    this.epss = await this.service.executeFetch('/epss');
    this.arls = await this.service.executeFetch('/arls');
  }

  async loadData() {
    if (this.idaprendiz) {
      this.aprendiz = await this.service.executeFetch('/aprendizeditempresa/' + this.idaprendiz);

      this.trabajadordocumento = new Trabajadordocumento({
        id: this.aprendiz.trabajador.id,
        adjuntodocumento: this.aprendiz.trabajador.adjuntodocumento,
        ext: this.aprendiz.trabajador.ext
      });

      this.buildFormTrabajador();
    } else {
      alert('El parametro id trabajador no esta definido');
    }
  }

  private buildFormTrabajador() {
    this.firstFormGroup = this.formBuilder.group({
      id: [this.aprendiz.trabajador.id],
      tipodocumento: [this.aprendiz.trabajador.tipodocumento, [Validators.required, Validators.pattern('[a-zA-Z]+')]],
      numerodocumento: [this.aprendiz.trabajador.numerodocumento, [Validators.required, Validators.pattern('[0-9]+'), Validators.maxLength(16)]],
      primernombre: [this.aprendiz.trabajador.primernombre, [Validators.required, Validators.maxLength(20)]],
      segundonombre: [this.aprendiz.trabajador.segundonombre, [Validators.maxLength(20)]],
      primerapellido: [this.aprendiz.trabajador.primerapellido, [Validators.required, Validators.maxLength(20)]],
      segundoapellido: [this.aprendiz.trabajador.segundoapellido, [Validators.maxLength(20)]],
      genero: [this.aprendiz.trabajador.genero, [Validators.required , Validators.maxLength(1)]],
      fechanacimiento: [this.aprendiz.trabajador.fechanacimiento, [Validators.required , Validators.maxLength(10)]],
      tiposangre: [this.aprendiz.trabajador.tiposangre, [Validators.required]],
      ocupacion: [this.aprendiz.trabajador.ocupacion],
      departamentodomicilio: [this.aprendiz.trabajador.departamentodomicilio],
      ciudaddomicilio: [this.aprendiz.trabajador.ciudaddomicilio],
      direcciondomicilio: [this.aprendiz.trabajador.direcciondomicilio, [Validators.required , Validators.maxLength(60)]],
      celular: [this.aprendiz.trabajador.celular, [Validators.maxLength(10)]],
      correoelectronico: [
        this.aprendiz.trabajador.correoelectronico,
        [Validators.email, Validators.minLength(5), Validators.maxLength(40)]
      ],
      adjuntodocumento: [this.aprendiz.trabajador.adjuntodocumento],
      ext: [this.aprendiz.trabajador.ext],
    });


    if (this.aprendiz.trabajador.departamentodomicilio !== undefined) {
      this.ciudadesDomicilio = this.departamentosService.getCiudades(this.aprendiz.trabajador.departamentodomicilio);
    }

    this.firstFormGroup.get('departamentodomicilio').valueChanges.subscribe(x => {
      this.ciudadesDomicilio = this.departamentosService.getCiudades(x);
    });
  }

   getErrorMessage() {
    return this.firstFormGroup.get('correoelectronico').hasError('required')
      ? 'You must enter a value'
      : this.firstFormGroup.get('correoelectronico').hasError('email')
        ? 'Not a valid email'
        : '';
  }

  public findInvalidControls(controls: any) {
    for (const name in controls) {
        if (controls[name].invalid) {
            this.invalid.push(' ' + name);
        }
    }
//    if ()
    return this.invalid.length;
  }

  transform(base64) {
    return this.sanitizer.bypassSecurityTrustResourceUrl('data:image/' + this.aprendiz.trabajador.ext + ';base64,' + base64);
  }

  async updateTrabajador() {
    let id;

    this.invalid = [];
    this.findInvalidControls(this.firstFormGroup.controls);

    if (this.firstFormGroup.invalid) {
      this.firstFormGroup.markAllAsTouched();
      if (this.invalid && this.invalid.length > 0) {
        alert('Los datos digitados no son válidos ');
      } else {
        alert('Los datos digitados no son válidos pero ...');
      }
      return;
    }

    if (!this.firstFormGroup.valid) {
      this.firstFormGroup.markAllAsTouched();
    }

    id = this.firstFormGroup.get('id').value;
    const datos = this.firstFormGroup.getRawValue();

    try {
      const r = await this.service.post('/updateTrabajador/' + id, datos);
      alert('Actualización exitosa');
    } catch (error) {
      alert(error);
    }
  }


  async updateFotoTrabajador() {
    try {
      if (!this.fototrabajador?.base64) {
        alert('No se ha seleccionado ninguna foto para actualizar');
        return;
      }

      const response = await this.service.post('/updateFoto', this.fototrabajador);

      if (response) {
        alert('La foto del trabajador se actualizó exitosamente');
      }
    } catch (error) {
      console.error('Error al actualizar la foto:', error);
      let mensajeError = 'Error al actualizar la foto del trabajador';

      if (typeof error === 'object' && error?.message) {
        mensajeError = error.message;
      }

      alert(mensajeError);
    }
  }

  onClickDocumentoA() {
    const fileUpload = this.fileUploada.nativeElement;

    fileUpload.click();
  }

  onClickDocumentoB() {
    const fileUpload = this.fileUploadb.nativeElement;

    fileUpload.click();
  }

  onClickDocumentoAprendiz(documento: Documento) {
    const fileUploadaprendiz = this.fileUploadaprendiz.nativeElement;

    this.documento = documento;
    fileUploadaprendiz.click();
  }


  onUploadFotoChange(evt: any) {
    const file = evt.target.files[0];

    if (file) {
      const reader = new FileReader();

      if (this.fototrabajador === undefined || this.fototrabajador == null) {
        this.fototrabajador = new Fototrabajador({
          id: this.aprendiz.trabajador.id,
        });
      }

      reader.onload = this.handleFotoReaderLoaded.bind(this);
      reader.readAsBinaryString(file);
    }
  }

  handleFotoReaderLoaded(e) {
    this.fototrabajador.base64 = btoa(e.target.result);
  }

  onUploadChangeAprendiz(evt: any, documento: Documento) {
    const file = evt.target.files[0];

    if (file) {
      const reader = new FileReader();

      this.documento = documento;
      this.documento.ext = file.name.split('.').pop();
      this.documento.nombrefile = file.name;

      reader.onload = this.handleAprendizReaderLoaded.bind(this);
      reader.readAsBinaryString(file);
    }
  }

  handleAprendizReaderLoaded(e) {
    this.documento.base64 = btoa(e.target.result);
  }

  async guardarDocumentos() {
//    let documentos: Documento[] = [];

    try {
      const resp = await this.service.post('/saveDocumentoaprendiz', this.aprendiz.documentos);

      this.aprendiz.documentos = resp;
    } catch (error) {
      alert(error);
    }
  }

  showPdf(base64: string) {
    const linkSource = 'data:application/pdf;base64,' + ' ' + base64;
    const downloadLink = document.createElement("a");
    const fileName = "doc.pdf";

    downloadLink.href = linkSource;
    downloadLink.download = fileName;
    downloadLink.click();
  }

  get correoelectronicoField() {
    return this.firstFormGroup.get('correoelectronico');
  }

  get isCorreoelectronicoFieldValid() {
    return this.correoelectronicoField.touched && this.correoelectronicoField.valid;
  }

  get isCorreoelectronicoFieldInvalid() {
    return this.correoelectronicoField.touched && this.correoelectronicoField.invalid;
  }
  get otralaborField() {
    return this.firstFormGroup.get('otralabor');
  }

  get isOtralaborFieldValid() {
    return this.otralaborField.touched && this.otralaborField.valid;
  }

  get isOtralaborFieldInvalid() {
    return this.otralaborField.touched && this.otralaborField.invalid;
  }

  get alergiasField() {
    return this.firstFormGroup.get('alergias');
  }

  get isAlergiasFieldValid() {
    return this.alergiasField.touched && this.alergiasField.valid;
  }

  get isAlergiasFieldInvalid() {
    return this.alergiasField.touched && this.alergiasField.invalid;
  }

  get medicamentosField() {
    return this.firstFormGroup.get('medicamentos');
  }

  get isMedicamentosFieldValid() {
    return this.medicamentosField.touched && this.medicamentosField.valid;
  }

  get isMedicamentosFieldInvalid() {
    return this.medicamentosField.touched && this.medicamentosField.invalid;
  }

  get enfermedadesField() {
    return this.firstFormGroup.get('enfermedades');
  }

  get isEnfermedadesFieldValid() {
    return this.enfermedadesField.touched && this.enfermedadesField.valid;
  }

  get isEnfermedadesFieldInvalid() {
    return this.enfermedadesField.touched && this.enfermedadesField.invalid;
  }

  get lesionesField() {
    return this.firstFormGroup.get('lesiones');
  }

  get isLesionesFieldValid() {
    return this.lesionesField.touched && this.lesionesField.valid;
  }

  get isLesionesFieldInvalid() {
    return this.lesionesField.touched && this.lesionesField.invalid;
  }

  get drogasField() {
    return this.firstFormGroup.get('drogas');
  }

  get isDrogasFieldValid() {
    return this.drogasField.touched && this.drogasField.valid;
  }

  get isDrogasFieldInvalid() {
    return this.drogasField.touched && this.drogasField.invalid;
  }

  get tiposangreField() {
    return this.firstFormGroup.get('tiposangre');
  }

  get isTiposangreFieldValid() {
    return this.tiposangreField.touched && this.tiposangreField.valid;
  }

  get isTiposangreFieldInvalid() {
    return this.tiposangreField.touched && this.tiposangreField.invalid;
  }

  get nombrecontactoField() {
    return this.firstFormGroup.get('nombrecontacto');
  }

  get isNombrecontactoFieldValid() {
    return this.nombrecontactoField.touched && this.nombrecontactoField.valid;
  }

  get isNombrecontactoFieldInvalid() {
    return this.nombrecontactoField.touched && this.nombrecontactoField.invalid;
  }

  get telefonocontactoField() {
    return this.firstFormGroup.get('telefonocontacto');
  }

  get isTelefonocontactoFieldValid() {
    return this.telefonocontactoField.touched && this.telefonocontactoField.valid;
  }

  get isTelefonocontactoFieldInvalid() {
    return this.telefonocontactoField.touched && this.telefonocontactoField.invalid;
  }

  get parentescocontactoField() {
    return this.firstFormGroup.get('parentescocontacto');
  }

  get isParentescocontactoFieldValid() {
    return this.parentescocontactoField.touched && this.parentescocontactoField.valid;
  }

  get isParentescocontactoFieldInvalid() {
    return this.parentescocontactoField.touched && this.parentescocontactoField.invalid;
  }
}
