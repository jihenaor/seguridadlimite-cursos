import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Router, ActivatedRoute, RouterLink } from '@angular/router';
import { debounceTime, map, startWith, tap } from 'rxjs/operators';

import { NivelsService } from './../../../core/service/nivels.service';
import { AuthService } from '../../../core/service/auth.service';
import { Eps } from '../../../core/models/eps.model';
import { Arl } from '../../../core/models/arl.model';
import { ServicesService } from '../../../core/service/services.service';

import { Documento } from '../../../core/models/documento.model';
import { Enfasis } from '../../../core/models/enfasis.model';
import { Empresa } from '../../../core/models/empresa.model';
import { Programa } from '../../../core/models/programa.model';
import { Ciudad } from '../../../core/models/ciudad.model';
import { Departamento } from '../../../core/models/departamento.model';
import { Grupo } from '../../../core/models/grupo.model';
import { Trabajador } from '../../../core/models/trabajador.model';
import { ThemePalette, MatOptionModule } from '@angular/material/core';

import { Nivel } from 'src/app/core/models/nivel.model';

import { DepartamentService } from 'src/app/core/service/departamentos.service';
import { PreinscripcionService } from './preinscripcion.service';
import { ShowNotificacionService } from 'src/app/core/service/show-notificacion.service';
import { Observable } from 'rxjs';
import { ParametrosFindService } from 'src/app/core/service/parametrosFind.service';

import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatSelectModule } from '@angular/material/select';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { NgIf, NgFor, AsyncPipe } from '@angular/common';
import { MatStepperModule } from '@angular/material/stepper';
import { MatButtonModule } from '@angular/material/button';

export interface PeriodicElement {
  nombre: string;
  id: number;
  nombrefile: string;
}

/** Flat node with expandable and level information */
interface ExampleFlatNode {
  expandable: boolean;
  name: string;
  id: number;
  level: number;
}

@Component({
    selector: 'app-preinscripcion',
    templateUrl: './preinscripcion.component.html',
    styleUrls: ['./preinscripcion.component.scss'],
    imports: [RouterLink, FormsModule,
        ReactiveFormsModule,
        MatButtonModule,
        MatStepperModule,
        NgIf,
        MatSlideToggleModule, MatFormFieldModule,
        MatInputModule, MatIconModule, MatSelectModule, MatOptionModule, NgFor, MatAutocompleteModule, AsyncPipe]
})
export class PreinscripcionComponent implements OnInit {
  @ViewChild("numerodocumento") numerodocumento : ElementRef;

  isLinear = false;
  firstFormGroup: FormGroup;
  programas: Programa[];
  nivels: Nivel[];
  empresas: Empresa[];
  enfasiss: Enfasis[];
  documentos: Documento[];
  documento: Documento;
  ciudadesDomicilio: Ciudad[] = [];
  departamentos: Departamento[] = [];
  epss: Eps[] = [];
  arls: Arl[] = [];
  public trabajadorvalidado = false;
  public trabajador: Trabajador;
  public grupos: Grupo[];
  public idprograma;
  public idgrupo;
  public idnivel;

  public existeTrabajador;
  public idempresa;
  public invalid = [];
  color: ThemePalette = 'accent';
  scanner = false;
  basico: string
  filteredEmpresas: Observable<Empresa[]>;

  fechaInscripcion = new FormControl();


  //  Tabla documentos
  displayedColumns: string[] = ['no', 'nombre', 'upload', 'nombrefile'];

  @ViewChild("fileUploadaprendiz", {static: false}) fileUploadaprendiz: ElementRef;
//  files  = [];

  @ViewChild("fileUpload", {static: false}) fileUpload: ElementRef;
  files  = [];

  public mask = {
    guide: true,
    showMask : true,
    mask: [/\d/, /\d/, '/', /\d/, /\d/, '/',/\d/, /\d/,/\d/, /\d/]
  };

  constructor(private formBuilder: FormBuilder,
              public services: ServicesService,
              private departamentosService: DepartamentService,
              private route: ActivatedRoute, private router: Router,
              private authService: AuthService,
              private nivelsService: NivelsService,
              private notificacionService: ShowNotificacionService,
//              private stateService: StateService,
              public parametrosFindService: ParametrosFindService,
              private preinscripcionService: PreinscripcionService) {
    this.route.params.subscribe(params => {
      this.idgrupo = params['idgrupo'];

      this.basico = params['basico'];
      if (!this.basico) {
        this.basico = 'N';
      } else {
        if (this.basico === 'S') {
          this.scanner = true;
        }
      }

      if (params['idprograma']) {
        this.idprograma = params['idprograma'];
      }

      if (params['idnivel']) {
        this.idnivel = params['idnivel'];
      }
    });

    this.idempresa = this.authService.getItem("idempresa");

    if (this.idempresa === ''){
      this.idempresa = undefined;
    }
    this.buildForm();

    this.departamentos = departamentosService.getDepartamentos();
  }

  async ngOnInit() {
    this.parametrosFindService.get();
    this.programas = await this.services.executeFetch('/programas/activoscondetalle');
    this.loadNiveles();
    this.empresas = await this.services.executeFetch('/empresas');

    if (this.idnivel) {
      this.documentos = await this.services.getDocumentos(parseInt(this.idnivel), undefined)
    } else {
      this.documentos = await this.services.executeFetch('/documentos');
    }

    this.epss = await this.services.executeFetch('/epss');
    this.arls = await this.services.executeFetch('/arls');

    this.numerodocumentoField.valueChanges
    .subscribe(value => {
      // console.log(value);
    });
  }

  private loadNiveles() {
    this.nivelsService.getNivelActivos().subscribe(
      (data: Nivel[]) => {
        this.nivels = data;
      }
    );
  }

  private buildForm() {
    this.firstFormGroup = this.formBuilder.group({
      tipodocumento: ['CC', [Validators.required, Validators.pattern('[a-zA-Z]+')]],
      numerodocumento: ['',
        [ Validators.required,
          Validators.pattern('[0-9]+'),
          Validators.maxLength(16),

        ], // MyValidators.validateExistDocument(this.services)
      ],
      primernombre: [{
                      value: '',
                      disabled: false
                    }, [Validators.required, Validators.pattern('[a-zA-ZÑÁÉÍÓÚ]+'), , Validators.maxLength(80)]],
      segundonombre: [{
                        value: '',
                        disabled: false
                      }],
      primerapellido: [{
                      value: '',
                      disabled: false
                    }, [Validators.required, Validators.pattern('[a-zA-ZÑÁÉÍÓÚ]+'), , Validators.maxLength(20)]],
      segundoapellido: [''],
      areatrabajo: ['', []],
      niveleducativo: [''],
      cargoactual: ['', [Validators.maxLength(45)]],
      genero: [''],
      fechanaciminetoscan: [''],
      tiposangre: ['', [Validators.maxLength(3)]],
      ocupacion: ['', [Validators.maxLength(50)]],
      departamentodomicilio: [''],
      ciudaddomicilio: [''],
      direcciondomicilio: [''],
      celular: ['', [Validators.pattern('[0-9]+'), Validators.maxLength(10)]],
      correoelectronico: [
        '',
        [Validators.email, Validators.maxLength(80)]
      ],
      eps: ['', [Validators.maxLength(40)]],
      arl: ['', [Validators.maxLength(40)]],
      sabeleerescribir: [''],
      labordesarrolla: ['',
              [Validators.maxLength(45)]],
      operativa: [''],
      administrativa: [''],
      otralabor: ['',
              [Validators.maxLength(20)]],
      alergias: ['',
              [Validators.maxLength(100)]],
      medicamentos: ['',
              [Validators.maxLength(100)]],
      enfermedades: ['',
              [Validators.maxLength(100)]],
      lesiones: ['',
              [Validators.maxLength(100)]],
      drogas: ['',
              [Validators.maxLength(100)]],
      nombrecontacto: ['',
              [Validators.maxLength(60)]],
      telefonocontacto: ['',
              [Validators.maxLength(45)]],
      parentescocontacto: ['',
              [Validators.maxLength(45)]],
      adjuntodocumento: [''],
      ext: [''],
      idprograma: [this.idprograma],
      idgrupo: [ this.idgrupo ],
      idenfasis: [],
      idnivel: [this.idnivel, [Validators.required]],
      pagocurso: [],
      empresa: [],
      nit: [],
      representantelegal: [],
      estadoinscripcion: ['P']
    });

   this.firstFormGroup.valueChanges
      .pipe(
        debounceTime(500)
      )
      .subscribe(value => {
//        console.log('Cambio algo', value);
      });

    this.firstFormGroup.get('numerodocumento').valueChanges
      .pipe(
        debounceTime(500)
      )
      .subscribe(x => {
        if (x && x.length > 0) {
          if (x.substring(0, 1) === '0') {
            this.firstFormGroup.get('numerodocumento').setValue(x.substring(1, x.substring(0, x.length)));
          }
        } else {
          this.firstFormGroup.get('fechanaciminetoscan').setValue('');
        }
      });

    this.firstFormGroup.get('departamentodomicilio').valueChanges.subscribe(x => {
      this.ciudadesDomicilio = this.departamentosService.getCiudades(x);
     });

    this.firstFormGroup.get('idprograma').valueChanges.subscribe(x => {
      const p =  this.programas.find(t => t.id === Number(x));
      if (p !== undefined) {
        this.grupos = p.grupos;
      }
      if (this.grupos === undefined || this.grupos.length === 0) {
        alert('No existen grupos activos');
      }
    });

    if (this.idnivel) {
      this.firstFormGroup.get('idnivel').setValue(this.idnivel);
    }

    this.filteredEmpresas = this.firstFormGroup.controls.empresa.valueChanges.pipe(
      startWith(''),
      map(value => this._filterEmpresa(value  || ''))
    );

  }

  private _filterEmpresa(value: string): Empresa[] {
    const filterValue = value.toLowerCase();
    return this.empresas ? this.empresas.filter(empresa => empresa.nombre.toLowerCase().includes(filterValue)) : [];
  }


  myFilter = (d: Date | null): boolean => {
    const day = (d || new Date()).getDay();
    // Prevent Saturday and Sunday from being selected.
    return day !== 0;
  }

  selectGrupo(id: number) {
//    this.sexcondFormGroup.patchValue( {
//      idgrupo: id
//    });
  }

  getBase64(file) {
    return new Promise((resolve, reject) => {
      const reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onload = () => resolve(reader.result);
      reader.onerror = error => reject(error);
    });
  }

  irTrabajador(idtrabajador: number, idaprendiz: number) {
    try {
//      this.stateService.idAprendiz = idaprendiz;
//      this.stateService.idTrabajador = idtrabajador;
      sessionStorage.setItem('idtrabajador', idtrabajador + '');
      sessionStorage.setItem('idaprendiz', idaprendiz + '');

      this.router.navigate(['/admin/students/about-aprendiz', idaprendiz]);
    } catch(error) {
      alert('Error al abrir la página de aprendiz' + error);
    }
  }


  uploadFile(file) {
    const formData = new FormData();
    formData.append('file', file.data);
    file.inProgress = true;
    /*
    this.uploadService.upload(formData).pipe(
      map(event => {
        switch (event.type) {
          case HttpEventType.UploadProgress:
            file.progress = Math.round(event.loaded * 100 / event.total);
            break;
          case HttpEventType.Response:
            return event;
        }
      }),
      catchError((error: HttpErrorResponse) => {
        file.inProgress = false;
        return of(`${file.data.name} upload failed.`);
      })).subscribe((event: any) => {
        if (typeof (event) === 'object') {
          console.log(event.body);
        }
      });
      */
  }

  onClickDocumentoAprendiz(documento: Documento) {
    const fileUploadaprendiz = this.fileUploadaprendiz.nativeElement;

    this.documento = documento;
    fileUploadaprendiz.click();
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

  async validarTrabajador() {
    const numerodocumento = this.firstFormGroup.get('numerodocumento').value;
    const tipodocumento = this.firstFormGroup.get('tipodocumento').value;
    let departamentodomicilio;

    if (tipodocumento === undefined || tipodocumento === null || tipodocumento.length === 0) {
      this.notificacionService.displayWarning('Seleccione un tipo de documento');
      return;
    }

    if (numerodocumento === undefined || numerodocumento === null || numerodocumento.length === 0) {
      this.notificacionService.displayWarning('Seleccione un número de documento');
      return;
    }

//    this.trabajador = await this.services.executeFetch('/trabajadornumerodocumento/' + numerodocumento);

    this.trabajador = await this.services.executeFetch('/trabajadorinscripcion/' + numerodocumento);

    this.existeTrabajador = this.trabajador !== undefined;

    if (this.trabajador !== undefined) {
      this.firstFormGroup.get('primernombre').enable();
      this.firstFormGroup.get('segundonombre').enable();
      this.firstFormGroup.get('primerapellido').enable();
      this.firstFormGroup.get('segundoapellido').enable();

      if (this.trabajador.id !== undefined && this.trabajador.id !== null) {
        this.existeTrabajador = true;
        departamentodomicilio = this.trabajador.departamentodomicilio;

      } else {
        this.existeTrabajador = false;
      }

//      this.firstFormGroup.controls['primernombre'].setValue(this.trabajador.primernombre);

      this.firstFormGroup.patchValue({
        id: this.trabajador.id,
        primernombre: this.trabajador.primernombre,
        segundonombre: this.trabajador.segundonombre,
        primerapellido: this.trabajador.primerapellido,
        segundoapellido: this.trabajador.segundoapellido,
        areatrabajo: this.trabajador.areatrabajo,
        genero: this.trabajador.genero,
        tiposangre: this.trabajador.tiposangre,
        ocupacion: this.trabajador.ocupacion,
        departamentodomicilio: this.trabajador.departamentodomicilio,
        ciudaddomicilio: this.trabajador.ciudaddomicilio,
        direcciondomicilio: this.trabajador.direcciondomicilio,
        celular: this.trabajador.celular,
        correoelectronico: this.trabajador.correoelectronico,
        adjuntodocumento: this.trabajador.adjuntodocumento,
        ext: this.trabajador.ext,
        eps: this.trabajador.eps,
      });

      if (departamentodomicilio !== undefined && departamentodomicilio !== null) {
        this.ciudadesDomicilio = this.departamentosService.getCiudades(this.firstFormGroup.get('departamentodomicilio').value);
      }
    } else {
      this.existeTrabajador = false;
    }

    this.trabajadorvalidado = true;
  }

  get numerodocumentoField() {
    return this.firstFormGroup.get('numerodocumento');
  }

  get isNumerodocumentoFieldValid() {
    return this.numerodocumentoField.touched && this.numerodocumentoField.valid;
  }

  get isNumerodocumentoFieldInvalid() {
    return this.numerodocumentoField.touched && this.numerodocumentoField.invalid;
  }

  get primerapellidoField() {
    return this.firstFormGroup.get('primerapellido');
  }

  get isPrimerapellidoFieldValid() {
    return this.primerapellidoField.touched && this.primerapellidoField.valid;
  }

  get isPrimerapellidoFieldInvalid() {
    return this.primerapellidoField.touched && this.primerapellidoField.invalid;
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

  public findInvalidControls(controls: any) {
    for (const name in controls) {
        if (controls[name].invalid) {
            this.invalid.push(' ' + name);
        }
    }
//    if ()
    return this.invalid.length;
  }

  async confirmAdd() {
    this.invalid = [];
    this.findInvalidControls(this.firstFormGroup.controls);

    if (this.firstFormGroup.invalid) {
      this.firstFormGroup.markAllAsTouched();
      this.authService.saveForm('trabajador', this.firstFormGroup.getRawValue());
      alert('Los datos digitados en el primer formulario no son válidos');
      return;
    }

    if (this.firstFormGroup.get('idgrupo').value === undefined || this.firstFormGroup.get('idgrupo').value === 0) {
      alert('El grupo es requerido');
      return;
    }

    if (this.firstFormGroup.get('idenfasis').value === undefined || this.firstFormGroup.get('idenfasis').value.length === 0) {
      alert('El enfasis es requerido');
      return;
    }

    if (this.firstFormGroup.invalid) {
      this.firstFormGroup.markAllAsTouched();
      alert('Los datos digitados en el segundo formulario no son válidos');
      return;
    } else {
    }

    this.preinscripcionService.save(this.firstFormGroup.getRawValue())
    .pipe(
      tap(() => {
          this.notificacionService.displaySuccess('La actualizacion fué exitosa');
      })
    )
    .subscribe();
  }

  changeScan() {
    this.scanner = !this.scanner;
    this.numerodocumento?.nativeElement.focus();

    var element = document.getElementById('numerodocumento');
    if (this.scanner) {
      if(element) {
        element.focus();
      } else {
        console.log('No existe elemento')
      }
    }
  }
}
