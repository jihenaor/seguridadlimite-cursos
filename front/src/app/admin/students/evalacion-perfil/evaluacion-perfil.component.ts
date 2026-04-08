import { ServicesService } from '../../../core/service/services.service';
import { UntypedFormBuilder, UntypedFormGroup, Validators, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';

import { DomSanitizer } from '@angular/platform-browser';

import { Trabajador } from '../../../core/models/trabajador.model';
import { Documento } from '../../../core/models/documento.model';
import { Fototrabajador } from '../../../core/models/fototrabajador.model';
import { Aprendiz } from '../../../core/models/aprendiz.model';
import { Departamento } from '../../../core/models/departamento.model';
import { Ciudad } from '../../../core/models/ciudad.model';
import { Trabajadordocumento } from '../../../core/models/trabajadordocumento.model';
import { DepartamentService } from 'src/app/core/service/departamentos.service';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatButtonModule } from '@angular/material/button';
import { MatOptionModule } from '@angular/material/core';
import { MatSelectModule } from '@angular/material/select';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatTabsModule } from '@angular/material/tabs';
import { NgIf, NgFor, DecimalPipe } from '@angular/common';

@Component({
    selector: 'app-evaluacion-perfil',
    templateUrl: './evaluacion-perfil.component.html',
    styleUrls: ['./evaluacion-perfil.component.sass'],
    imports: [RouterLink, NgIf, MatTabsModule, FormsModule, ReactiveFormsModule, MatFormFieldModule, MatSelectModule, MatOptionModule, MatButtonModule, MatExpansionModule, MatInputModule, MatIconModule, NgFor, DecimalPipe]
})
export class EvaluacionperfilComponent implements OnInit {
//  public student: Trabajador;
  public aprendiz: Aprendiz;
  public idaprendiz: number;
  public firstFormGroup: UntypedFormGroup;
  public secondFormGroup: UntypedFormGroup;
  public aprendizFormGroup: UntypedFormGroup;
  public panelOpenState = false;
  public fototrabajador: Fototrabajador;
  public documento: Documento;
  public trabajadordocumento: Trabajadordocumento;
  public departamentos: Departamento[] = [];
  public ciudadesDomicilio: Ciudad[] = [];

  fileUploadForm: UntypedFormGroup;

//  @ViewChild("fileUploada", {static: false}) fileUploada: ElementRef;
//  @ViewChild("fileUploadb", {static: false}) fileUploadb: ElementRef;

//  @ViewChild("fileUploadaprendiz", {static: false}) fileUploadaprendiz: ElementRef;
//  files  = [];

  constructor(private formBuilder: UntypedFormBuilder,
              public service: ServicesService,
              private departamentosService: DepartamentService,
              private route: ActivatedRoute,
              private fb: UntypedFormBuilder,
              private sanitizer: DomSanitizer) {
    this.route.params.subscribe(params => {
      this.idaprendiz = params['idaprendiz'];
    });
  }

  async ngOnInit() {
    this.departamentos = this.departamentosService.getDepartamentos();
    this.loadData();

    this.fileUploadForm = this.formBuilder.group({
      ladoa: [''],
      ladob: [''],
      file: ['']
    });
  }

  transform(base64) {
    return this.sanitizer.bypassSecurityTrustResourceUrl('data:image/' + this.aprendiz.trabajador.ext + ';base64,' + base64);
  }

  showPdf(base64: string) {
    const linkSource = 'data:application/pdf;base64,' + ' ' + base64;
    const downloadLink = document.createElement("a");
    const fileName = "doc.pdf";

    downloadLink.href = linkSource;
    downloadLink.download = fileName;
    downloadLink.click();
  }

  async loadData() {
    if (this.idaprendiz) {
      this.aprendiz = await this.service.executeFetch('/aprendize/' + this.idaprendiz);

      this.trabajadordocumento = new Trabajadordocumento({
        id: this.aprendiz.trabajador.id,
        adjuntodocumento: this.aprendiz.trabajador.adjuntodocumento,
        ext: this.aprendiz.trabajador.ext
      });

      this.buildFormTrabajador();
    } else {
      alert('No se han definido los parametros del trabajador o del aprendiz');
    }
  }

  private buildFormTrabajador() {
    this.firstFormGroup = this.formBuilder.group({
      id: [this.aprendiz.id],
      tipodocumento: [this.aprendiz.trabajador.tipodocumento, [Validators.required, Validators.pattern('[a-zA-Z]+')]],
      numerodocumento: [this.aprendiz.trabajador.numerodocumento, [Validators.required, Validators.pattern('[0-9]+'), , Validators.maxLength(16)]],
      primernombre: [this.aprendiz.trabajador.primernombre, [Validators.required, Validators.maxLength(20)]],
      segundonombre: [this.aprendiz.trabajador.segundonombre],
      primerapellido: [this.aprendiz.trabajador.primerapellido, [Validators.required, Validators.maxLength(20)]],
      segundoapellido: [this.aprendiz.trabajador.segundoapellido],
      areatrabajo: [this.aprendiz.trabajador.areatrabajo, [Validators.required]],
      genero: [this.aprendiz.trabajador.genero],
      nacionalidad: [this.aprendiz.trabajador.nacionalidad],
      tiposangre: [this.aprendiz.trabajador.tiposangre],
      ocupacion: [this.aprendiz.trabajador.ocupacion],
      departamentodomicilio: [this.aprendiz.trabajador.departamentodomicilio],
      ciudaddomicilio: [this.aprendiz.trabajador.ciudaddomicilio],
      direcciondomicilio: [this.aprendiz.trabajador.direcciondomicilio],
      celular: [this.aprendiz.trabajador.celular],
      correoelectronico: [
        this.aprendiz.trabajador.correoelectronico,
        [Validators.email, Validators.minLength(5), Validators.maxLength(40)]
      ],

      adjuntodocumento: [this.aprendiz.trabajador.adjuntodocumento],
      ext: [this.aprendiz.trabajador.ext],
    });

    this.secondFormGroup = this.formBuilder.group({
      id: [this.aprendiz.id],
      cumplehoras: [this.aprendiz.cumplehoras, [Validators.required]],
      idempresa: [this.aprendiz.idempresa ? this.aprendiz.idempresa : undefined],
      evaluacionformacion: [this.aprendiz.evaluacionformacion, [Validators.required]],
      evaluacionentrenamiento: [this.aprendiz.evaluacionentrenamiento, [Validators.required]],
      eps: [this.aprendiz.eps],
      arl: [this.aprendiz.arl],
      sabeleerescribir: [this.aprendiz.sabeleerescribir],
      labordesarrolla: [this.aprendiz.labordesarrolla],
      operativa: [this.aprendiz.operativa, [Validators.maxLength(1)]],
      administrativa: [this.aprendiz.administrativa, [Validators.maxLength(1)]],
      otralabor: this.aprendiz.otralabor,
      alergias: [this.aprendiz.alergias],
      medicamentos: [this.aprendiz.medicamentos],
      enfermedades: [this.aprendiz.enfermedades],
      lesiones: [this.aprendiz.lesiones],
      drogas: [this.aprendiz.drogas],
      nombrecontacto: [this.aprendiz.nombrecontacto],
      telefonocontacto: [this.aprendiz.telefonocontacto],
      parentescocontacto: [this.aprendiz.parentescocontacto],
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

  async updateAprendiz() {
    let r;
    if (this.secondFormGroup.valid) {

    } else {
      this.firstFormGroup.markAllAsTouched();
      alert('Los datos digitados no son validos');
      return;
    }

    const datos = this.secondFormGroup.getRawValue();

    try {
      r = await this.service.post('/aprendiz/updatePerfil', datos);
      alert('Actualización exitosa');
    } catch (error) {

      alert(error);
    }
  }

}
