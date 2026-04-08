import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { UntypedFormBuilder, UntypedFormGroup, Validators, FormsModule, ReactiveFormsModule } from '@angular/forms';

import { SaveTrabajadorAprendizService } from './../../core/service/savetrabajadoraprendiz.service';
import { ServicesService } from './../../core/service/services.service';

import { Ciudad } from '../../core/models/ciudad.model';
import { Departamento } from '../../core/models/departamento.model';
import { Eps } from '../../core/models/eps.model';
import { Arl } from '../../core/models/arl.model';
import { MyValidators } from '../../utils/validators';
import { Empresa } from '../../core/models/empresa.model';
import { Documento } from '../../core/models/documento.model';

import {Observable} from 'rxjs';
import {map, startWith, tap} from 'rxjs/operators';

import { ShowNotificacionService } from '../../core/service/show-notificacion.service';
import { MatDialog } from '@angular/material/dialog';
import { DepartamentService } from '../../core/service/departamentos.service';
import { TerminosCondicionesComponent } from './terminos-condiciones/terminos-condiciones.component';
import { MatButtonToggleModule } from '@angular/material/button-toggle';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { NgxMaskDirective } from 'ngx-mask';
import { MatInputModule } from '@angular/material/input';
import { MatOptionModule } from '@angular/material/core';
import { MatSelectModule } from '@angular/material/select';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { NgIf, NgFor, AsyncPipe } from '@angular/common';
import { FormSearchComponent } from '../components/form-search/form-search.component';
import { Header2Component } from '../components/header2/header2.component';
import { Grupo } from '../../core/models/grupo.model';
import { GruposAsociadosComponent } from './gruposAsociados/grupos-asociados.component';
import { NivelsService } from '../../core/service/nivels.service';
import { Nivel } from '../../core/models/nivel.model';
import { Aprendiz } from '../../core/models/aprendiz.model';
import { Trabajador } from '../../core/models/trabajador.model';
import { InscripcionesAbiertasComponent } from '../../shared/components/inscripciones-abiertas/inscripciones-abiertas.component';
import { InscripcionesAbiertasService } from '../../core/service/inscripciones-abiertas.service';
import { EnfasisService } from '../../admin/enfasis/enfasis.service';

@Component({
    selector: 'app-inscription',
    templateUrl: './inscription.component.html',
    styleUrls: ['./inscription.component.scss'],
    imports: [
        Header2Component,
        FormSearchComponent,
        NgIf,
        MatButtonModule,
        RouterLink,
        MatIconModule,
        FormsModule,
        ReactiveFormsModule,
        MatFormFieldModule,
        MatSelectModule,
        MatOptionModule,
        NgFor,
        MatInputModule,
        NgxMaskDirective,
        MatAutocompleteModule,
        MatButtonToggleModule,
        TerminosCondicionesComponent,
        AsyncPipe,
        GruposAsociadosComponent,
        InscripcionesAbiertasComponent
    ]
})
export class InscriptionComponent implements OnInit {
  authForm: UntypedFormGroup;
  authFormTrabajador: UntypedFormGroup;
  selected = false;
  horarioSeleccionado = false;
  mostrarEmbarazo = false;
  mostrarOtroEnfasis = false;
  selectedTrabajador = false;
  mostrarFormularioGrupo = false;
  error = '';
  hide = true;
  grupos: Grupo[];
  grupo: Grupo;
  public trabajador: Trabajador;
  firstFormGroup: UntypedFormGroup;
  ciudadesDomicilio: Ciudad[] = [];
  departamentos: Departamento[] = [];
  epss: Eps[] = [];
  arls: Arl[] = [];
  laboresdesarrolla: string[] = ['HSE', 'SST', 'INSPECTOR', 'SUPERVISOR', ''];
  filteredLabordesarrollas: Observable<string[]>;
  filteredEmpresas: Observable<Empresa[]>;

  public pelfil = 'APRENDIZ';
  public invalid = [];
  empresas: Empresa[];
  fileUploadForm: UntypedFormGroup;

  existeTrabajador = false;
//  mostrarListadoHorarios = false;
  documento: Documento;
  mostrarformulario = false;
  mostrardatostrabajador = false;
  mostrarlistadocursos = false;
  valido= false;
  aceptarterminos = false;

  public tienealergias=false;
  public tienemedicamentos = false;
  public tieneenfermedades = false;
  public tienelesiones = false;
  public tienedrogas = false;
  anos: string[] = [];
  meses: string[] = [];
  niveles: Nivel[] = [];
  public inscripcionesAbiertas = true;

  constructor(
    private formBuilder: UntypedFormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private services: ServicesService,
    private departamentosService: DepartamentService,
    public dialog: MatDialog,
    public enfasisService: EnfasisService,
    private saveTrabajadorAprendizService: SaveTrabajadorAprendizService,
    private notificacionService: ShowNotificacionService,
    private nivelesServicio: NivelsService,
    private inscripcionesService: InscripcionesAbiertasService
  ) {

    //1094947445
    this.authFormTrabajador = this.formBuilder.group({
      username: ['', Validators.required]
    });

    this.fileUploadForm = this.formBuilder.group({
      file: ['']
    });

    this.departamentos = departamentosService.getDepartamentos();

    for (let i = 0; i <= 50; i++) {
      this.anos.push(i + '');
    }
    for (let i = 1; i <= 12; i++) {
      this.meses.push(i + '');
    }

    this.route.queryParams.subscribe(params => {
      const {empresa} = params;
    });
  }
  async ngOnInit() {
    this.empresas = await this.services.executeFetch('/empresas');
    this.epss = await this.services.executeFetch('/epss');
    this.arls = await this.services.executeFetch('/arls');
    this.enfasisService.getAllEnfasis('Inscripcion');
    this.cargarNiveles();
    this.verificarInscripciones();
  }

  async getGrupos() {
    this.grupos = await this.services.executeFetch('/gruposactivosinscripcion');
  }

  get f() {
    return this.authForm.controls;
  }

  verificarInscripciones() {
    this.inscripcionesService.verificarInscripcionesAbiertas().subscribe({
      next: (abiertas) => {
        this.inscripcionesAbiertas = abiertas;
      },
      error: (error) => {
        console.error('Error al verificar inscripciones:', error);
        this.inscripcionesAbiertas = false;
      }
    });
  }

  cargarNiveles(): void {
    this.nivelesServicio.getNivelActivosFecha().subscribe(
      (data) => {
        this.niveles = data;
      },
      (error) => {
        console.error('Error al cargar niveles:', error);
      }
    );
  }

  myFilter = (d: Date | null): boolean => {
    const day = (d || new Date()).getDay();
    // Prevent Saturday and Sunday from being selected.
    return day !== 0;
  }

  async loginAdmin() {
    this.error = '';

    if (this.authForm.invalid) {
      this.error = 'Username and Password not valid !';
      return;
    } else {
      try {
        const res = await this.services.post('/authenticate', this.authForm.getRawValue());

        if (res && res.token !== undefined) {

        } else {
          alert('Fallo en la autenticación');
        }
      } catch (err) {
        alert('Se ha presentado un error o el usuario o la clave son incorrectos');
      }
    }
  }

  async onSubmit() {

  }

  async selectGrupo(grupo: Grupo) {
    this.selected = true;
    this.grupos.forEach(g => g.selected = false);
    grupo.selected = true;
    this.grupo = grupo;

    this.mostrarformulario = true;

    setTimeout( () => {
      document.getElementById('tipodocumento').click()
      document.getElementById('titulo_ficha_inscripcion').scrollIntoView({ behavior: 'smooth', block: 'start' });
     }, 1000 );
  }

  cancelGrupo() {
    this.selected = false;
    this.mostrarformulario = false;
    this.selectedTrabajador = false;
    this.grupos?.forEach(g => g.selected = false);

//    this.mostrarListadoHorarios = false;
    this.mostrarlistadocursos = false;

    sessionStorage.removeItem("numerodocumento")      
  }

  crearFormGroup(trabajador: Trabajador, numerodocumento: string) {
    let departamentodomicilio;

    if(trabajador.alergias?.length > 3) {
      this.tienealergias = true;
    }

    if(trabajador.medicamentos?.length > 3) {
      this.tienemedicamentos = true;
    }

    if(trabajador.enfermedades?.length > 3) {
      this.tieneenfermedades = true;
    }

    if(trabajador.lesiones?.length > 3) {
      this.tienelesiones = true;
    }

    if(trabajador.drogas?.length > 3) {
      this.tienedrogas = true;
    }

    this.firstFormGroup = this.formBuilder.group({
      id: [this.trabajador.id],
      tipodocumento: [this.trabajador.tipodocumento, [Validators.required, Validators.pattern('[a-zA-Z]+')]],
      numerodocumento: [{ value: numerodocumento, disabled: true },
        [ Validators.required,
          Validators.pattern('[0-9]+'),
          Validators.maxLength(16),
        ], // MyValidators.validateExistDocument(this.services)
      ],
      primerapellido: [{
                        value: this.trabajador.primerapellido,
                        disabled: this.valido},
                     [Validators.required, Validators.maxLength(20)]],
      segundoapellido: [{
                        value: this.trabajador.segundoapellido,
                        disabled: this.valido
                      }, [Validators.maxLength(20)]],
      primernombre: [{
                      value: this.trabajador.primernombre,
                      disabled: this.valido
                    }, [Validators.required, Validators.maxLength(80)]],
      segundonombre: [{
                        value: this.trabajador.segundonombre,
                        disabled: this.valido
                      }, [Validators.maxLength(20)]],
      areatrabajo: [this.trabajador.areatrabajo, []],
      niveleducativo: [this.trabajador.niveleducativo],
      cargoactual: [this.trabajador.cargoactual, [Validators.maxLength(45)]],
      genero: [{
                  value: this.trabajador.genero,
                  disabled: this.valido
              }, [Validators.required]],
      tiposangre: [this.trabajador.tiposangre, [Validators.required, Validators.maxLength(3)]],
      ocupacion: [this.trabajador.ocupacion, [Validators.maxLength(50)]],
      departamentodomicilio: [this.trabajador.departamentodomicilio, [Validators.required]],
      ciudaddomicilio: [this.trabajador.ciudaddomicilio, [Validators.required]],
      direcciondomicilio: [this.trabajador.direcciondomicilio, [Validators.required]],
      celular: [this.trabajador.celular, [Validators.pattern('[0-9]+'), Validators.maxLength(10)]],
      fechanacimiento: [this.trabajador.fechanacimiento, [Validators.required, Validators.maxLength(10), MyValidators.validBirthDate]],
      correoelectronico: [this.trabajador.correoelectronico, [Validators.email, Validators.maxLength(80)]],
      eps: [this.trabajador.eps, [Validators.required]],
      arl: [this.trabajador.arl, [Validators.required]],
      sabeleerescribir: [this.trabajador.sabeleerescribir, [Validators.required]],
      labordesarrolla: [this.trabajador.labordesarrolla, [Validators.required, Validators.maxLength(45)]],
      alergias: [this.trabajador.alergias, [Validators.maxLength(100)]],
      medicamentos: [this.trabajador.medicamentos, [Validators.maxLength(100)]],
      enfermedades: [this.trabajador.enfermedades, [Validators.maxLength(100)]],
      lesiones: [this.trabajador.lesiones, [Validators.maxLength(100)]],
      drogas: [this.trabajador.drogas, [Validators.maxLength(100)]],
      nombrecontacto: [this.trabajador.nombrecontacto, [Validators.required, Validators.maxLength(60)]],
      telefonocontacto: [this.trabajador.telefonocontacto, [Validators.required, Validators.maxLength(45)]],
      parentescocontacto: [this.trabajador.parentescocontacto, [Validators.required, Validators.maxLength(45)]],
      embarazo: [this.trabajador.embarazo, [Validators.maxLength(45)]],
      mesesgestacion: [this.trabajador.mesesgestacion, [Validators.maxLength(45)]],
      ext: [''],
      idaprendiz: [this.trabajador.idaprendiz],
      idprograma: [],
      idgrupo: [this.trabajador.idgrupo],
      idenfasis: [this.trabajador.idenfasis, [Validators.required]],
      otroenfasis: [this.trabajador.otroenfasis, [Validators.maxLength(30)]],
      idnivel: [this.trabajador.idnivel, [Validators.required]],
      pagocurso: ['N'],
      empresa: [this.trabajador.empresa, [Validators.required, Validators.maxLength(80)]],
      estadoinscripcion: ['P']
    }, {
      validators: MyValidators.validDocumentType
    });

    this.firstFormGroup.get('departamentodomicilio').valueChanges.subscribe(x => {
      this.ciudadesDomicilio = this.departamentosService.getCiudades(x);
    });

    this.firstFormGroup.get('genero').valueChanges.subscribe(x => {
      this.mostrarEmbarazo = x === 'F';
    });

    this.firstFormGroup.get('idenfasis').valueChanges.subscribe(x => {
      this.mostrarOtroEnfasis = x === 9;
    });

    if (this.trabajador !== undefined) {
      if (this.trabajador.id !== undefined && this.trabajador.id !== null) {
        this.existeTrabajador = true;
        departamentodomicilio = this.trabajador.departamentodomicilio;

      } else {
        this.existeTrabajador = false;
      }

      if (departamentodomicilio !== undefined && departamentodomicilio !== null) {
        this.ciudadesDomicilio = this.departamentosService.getCiudades(this.firstFormGroup.get('departamentodomicilio').value);
      }
    }

    this.filteredLabordesarrollas = this.firstFormGroup.controls.labordesarrolla.valueChanges.pipe(
      startWith(''),
      map(value => this._filterLabordesarrollo(value  || ''))
    );

    this.filteredEmpresas = this.firstFormGroup.controls.empresa.valueChanges.pipe(
      startWith(''),
      map(value => this._filterEmpresa(value  || ''))
    );

  }

  async validarTrabajador(trabajador: Trabajador) {
    this.trabajador = trabajador;
    sessionStorage.setItem('idtrabajador', this.trabajador.id + '');
    const numerodocumento = trabajador.numerodocumento;
//    const tipodocumento = this.firstFormGroup.get('tipodocumento').value;

    if (this.trabajador) {
      if (this.trabajador.existeinscripcionabierta)
      this.mostrarformulario = this.trabajador.idaprendiz !== null;
      this.valido = this.trabajador.valido === 'S';

    }

    this.mostrarlistadocursos = !this.mostrarformulario;
    this.crearFormGroup(this.trabajador, numerodocumento);

    this.selectedTrabajador = true;
    this.existeTrabajador = !(!this.trabajador.id || this.trabajador.id === null);
    if (this.grupo) {
      this.grupo.selected = false;
    }

    if (!trabajador.existeinscripcionabierta) {
      if (this.trabajador.id != null && trabajador.aprendizContinuaAprendizaje === false) {
        alert('Hola ' + trabajador.primernombre + ' ' + trabajador.segundonombre + ' '  + trabajador.primerapellido + ' ' + trabajador.segundoapellido + ', actualmente no se encuentra inscrito en ningun proceso de formacion')
      } else {
        alert('No existen inscripciones abiertas');
      }
    }

    this.mostrardatostrabajador = trabajador.aprendizContinuaAprendizaje;

    this.mostrarformulario = trabajador.existeinscripcionabierta;
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

  //
  get primernombreField() {
    return this.firstFormGroup.get('primernombre');
  }

  get isPrimernombreFieldValid() {
    return this.primernombreField.touched && this.primernombreField.valid;
  }

  get isPrimernombreFieldInvalid() {
    return this.primernombreField.touched && this.primernombreField.invalid;
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

  get tiposangreField() {
    return this.firstFormGroup.get('tiposangre');
  }

  get isTiposangreFieldValid() {
    return this.tiposangreField.touched && this.tiposangreField.valid;
  }

  get isTiposangreFieldInvalid() {
    return this.tiposangreField.touched && this.tiposangreField.invalid;
  }

  get fechanacimientoField() {
    return this.firstFormGroup.get('fechanacimiento');
  }

  get isFechanacimientoFieldValid() {
    return this.fechanacimientoField.touched && this.fechanacimientoField.valid;
  }

  get isFechanacimientoFieldInvalid() {
    return this.fechanacimientoField.touched && this.fechanacimientoField.invalid;
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

  get direcciondomicilioField() {
    return this.firstFormGroup.get('direcciondomicilio');
  }

  get isDirecciondomicilioFieldInvalid() {
    return this.direcciondomicilioField.touched && this.direcciondomicilioField.invalid;
  }

  get departamentodomicilioField() {
    return this.firstFormGroup.get('departamentodomicilio');
  }

  get isDepartamentodomicilioFieldInvalid() {
    return this.departamentodomicilioField.touched && this.departamentodomicilioField.invalid;
  }

  get isDepartamentodomicilioFieldValid() {
    return this.departamentodomicilioField.touched && this.departamentodomicilioField.valid;
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

  validarCampos() {
    if (this.firstFormGroup.controls["eps"].invalid) {
      alert('La EPS es requerida');

      document.getElementById('eps').scrollIntoView({ behavior: 'smooth', block: 'start' });
      return false;
    }

    if (this.firstFormGroup.controls["arl"].invalid) {
      alert('La ARL es requerida');

      document.getElementById('arl').scrollIntoView({ behavior: 'smooth', block: 'start' });
      return false;
    }

    if (this.firstFormGroup.controls["numerodocumento"].invalid) {
      alert('El numero de documento es requerido');

      document.getElementById('numerodocumento').scrollIntoView({ behavior: 'smooth', block: 'start' });
      return false;
    }

    if (this.firstFormGroup.controls["primerapellido"].invalid) {
      alert('El primer apellido es requerido');

      document.getElementById('primerapellido').scrollIntoView({ behavior: 'smooth', block: 'start' });
      return false;
    }

    if (this.firstFormGroup.controls["primernombre"].invalid) {
      alert('El primer nombre es requerido');

      document.getElementById('primernombre').scrollIntoView({ behavior: 'smooth', block: 'start' });
      return false;
    }
    if (this.firstFormGroup.controls["genero"].invalid) {
      alert('El genero es requerido');

      document.getElementById('genero').scrollIntoView({ behavior: 'smooth', block: 'start' });
      return false;
    }

    if (this.firstFormGroup.controls["correoelectronico"].invalid || this.firstFormGroup.get('correoelectronico').value === undefined  || this.firstFormGroup.get('correoelectronico').value === 0) {
      this.notificacionService.displayError('El correo electronico requerido');

      document.getElementById('correoelectronico').scrollIntoView({ behavior: 'smooth', block: 'start' });
      return false;
    }

    const emailRegex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;

    const emailValue = this.firstFormGroup.get('correoelectronico').value;
    if (!emailValue || !emailRegex.test(emailValue)) {
      this.notificacionService.displayError('El correo electrónico no es válido');

      document.getElementById('correoelectronico').scrollIntoView({ behavior: 'smooth', block: 'start' });
      return false;
    }

    if (this.firstFormGroup.controls["tiposangre"].invalid || this.firstFormGroup.get('tiposangre').value === undefined  || this.firstFormGroup.get('tiposangre').value === 0) {
      alert('El tipo de sangre es requerido');

      document.getElementById('tiposangre').scrollIntoView({ behavior: 'smooth', block: 'start' });
      return false;
    }

    if (this.firstFormGroup.controls["fechanacimiento"].invalid) {
      const fechaControl = this.firstFormGroup.get('fechanacimiento');
      if (fechaControl?.hasError('required')) {
        alert('La fecha de nacimiento es requerida');
      } else if (fechaControl?.hasError('invalid_date_format')) {
        alert('El formato de fecha debe ser AAAA-MM-DD');
      } else if (fechaControl?.hasError('invalid_month')) {
        alert('El mes debe estar entre 1 y 12');
      } else if (fechaControl?.hasError('invalid_day')) {
        alert('El día no es válido para el mes y año especificados');
      } else if (fechaControl?.hasError('future_date')) {
        alert('La fecha de nacimiento no puede ser en el futuro');
      } else if (fechaControl?.hasError('too_old')) {
        alert('La fecha de nacimiento no puede ser mayor a 120 años');
      } else {
        alert('La fecha de nacimiento no es válida');
      }

      document.getElementById('fechanacimiento').scrollIntoView({ behavior: 'smooth', block: 'start' });
      return false;
    }

    if (this.firstFormGroup.controls["empresa"].invalid || this.firstFormGroup.get('empresa').value === undefined  || this.firstFormGroup.get('empresa').value === 0) {
      alert('La empresa es requerida');

      document.getElementById('empresa').scrollIntoView({ behavior: 'smooth', block: 'start' });
      return false;
    }

    if (this.firstFormGroup.get('idgrupo').value === undefined || this.firstFormGroup.get('idgrupo').value === 0) {
      alert('El grupo es requerido');
      return false;
    }

    if (this.firstFormGroup.get('idenfasis').value === undefined || this.firstFormGroup.get('idenfasis').value === null || this.firstFormGroup.get('idenfasis').value.length === 0) {
      alert('El enfasis es requerido');
      document.getElementById('idenfasis').scrollIntoView({ behavior: 'smooth', block: 'start' });
      return false;
    }

    if (this.tienealergias) {
      if (this.firstFormGroup.controls["alergias"].invalid || this.firstFormGroup.controls["alergias"].value.length < 4) {
        alert('Las alergias son requeridas');

        document.getElementById('alergias').scrollIntoView({ behavior: 'smooth', block: 'start' });
        return false;
      }
    } else {
      this.firstFormGroup.controls["alergias"].setValue("");
    }

    if (this.tieneenfermedades) {
      if (this.firstFormGroup.controls["enfermedades"].invalid || this.firstFormGroup.controls["enfermedades"].value.length < 4) {
        alert('Las enfermedades son requeridas');

        document.getElementById('enfermedades').scrollIntoView({ behavior: 'smooth', block: 'start' });
        return false;
      }
    } else {
      this.firstFormGroup.controls["enfermedades"].setValue("");
    }

    if (this.tienemedicamentos) {
      if (this.firstFormGroup.controls["medicamentos"].invalid || this.firstFormGroup.controls["medicamentos"]?.value.length < 4) {
        alert('Los medicamentos son requeridos');

        document.getElementById('medicamentos').scrollIntoView({ behavior: 'smooth', block: 'start' });
        return false;
      }
    } else {
      this.firstFormGroup.controls["medicamentos"].setValue('');
    }

    if (this.tienelesiones) {
      if (this.firstFormGroup.controls["lesiones"].invalid || this.firstFormGroup.controls["lesiones"]?.value.length < 4) {
        alert('Los datos de lesiones son requeridos');

        document.getElementById('lesiones').scrollIntoView({ behavior: 'smooth', block: 'start' });
        return false;
      }
    } else {
      this.firstFormGroup.controls["lesiones"].setValue('');
    }

    if (this.tienedrogas) {
      if (this.firstFormGroup.controls["drogas"].invalid || this.firstFormGroup.controls["drogas"]?.value.length < 4) {
        alert('Los sobre el consumo de alcohol o drogas son requeridos');

        document.getElementById('drogas').scrollIntoView({ behavior: 'smooth', block: 'start' });
        return false;
      }
    } else {
      this.firstFormGroup.controls["drogas"].setValue("");
    }

    if (this.firstFormGroup.controls["nombrecontacto"].invalid) {
      alert('El nombre del contacto es requerido');

      document.getElementById('nombrecontacto').scrollIntoView({ behavior: 'smooth', block: 'start' });
      return false;
    }

    if (this.firstFormGroup.controls["telefonocontacto"].invalid) {
      alert('El telefono del contacto es requerido');

      document.getElementById('telefonocontacto').scrollIntoView({ behavior: 'smooth', block: 'start' });
      return false;
    }

    if (this.firstFormGroup.controls["parentescocontacto"].invalid) {
      alert('El parentesco del contacto es requerido');

      document.getElementById('parentescocontacto').scrollIntoView({ behavior: 'smooth', block: 'start' });
      return false;
    }

    if (this.firstFormGroup.controls["sabeleerescribir"].invalid) {
      alert('Presenta dificultad de lecto escritura es requerido');

      document.getElementById('sabeleerescribir').scrollIntoView({ behavior: 'smooth', block: 'start' });
      return false;
    }

    if (this.firstFormGroup.controls["labordesarrolla"].invalid) {
      alert('Labor que desarrolla es requerido');

      document.getElementById('labordesarrolla').scrollIntoView({ behavior: 'smooth', block: 'start' });
      return false;
    }

    return true;
  }

  async save() {
    this.invalid = [];

      this.findInvalidControls(this.firstFormGroup.controls);
      if (this.validarCampos() === false) {
        return;
      }

      if (this.firstFormGroup.invalid && this.invalid && this.invalid.length > 0) {
        this.firstFormGroup.markAllAsTouched();

        alert('Los datos digitados no son válidos');
        return;
      }

      // Validación adicional específica para fecha de nacimiento
      const fechaNacimientoControl = this.firstFormGroup.get('fechanacimiento');
      if (fechaNacimientoControl && fechaNacimientoControl.invalid) {
        this.firstFormGroup.markAllAsTouched();
        if (fechaNacimientoControl.hasError('required')) {
          alert('La fecha de nacimiento es requerida');
        } else if (fechaNacimientoControl.hasError('invalid_date_format')) {
          alert('El formato de fecha debe ser AAAA-MM-DD');
        } else if (fechaNacimientoControl.hasError('invalid_month')) {
          alert('El mes debe estar entre 1 y 12. Por favor verifique la fecha de nacimiento.');
        } else if (fechaNacimientoControl.hasError('invalid_day')) {
          alert('El día no es válido para el mes y año especificados. Por favor verifique la fecha de nacimiento.');
        } else if (fechaNacimientoControl.hasError('future_date')) {
          alert('La fecha de nacimiento no puede ser en el futuro');
        } else if (fechaNacimientoControl.hasError('too_old')) {
          alert('La fecha de nacimiento no puede ser mayor a 120 años');
        } else {
          alert('La fecha de nacimiento no es válida. Por favor verifique el formato AAAA-MM-DD.');
        }
        document.getElementById('fechanacimiento')?.scrollIntoView({ behavior: 'smooth', block: 'start' });
        return;
      }

    this.saveTrabajadorAprendizService.save(this.firstFormGroup.getRawValue())
      .pipe(
        tap((resp : Aprendiz) => {
          this.mostrarformulario = false;
          this.authFormTrabajador.controls["username"].setValue('');

          sessionStorage.setItem("idaprendiz", resp.id + '')
          sessionStorage.setItem("numerodocumento", resp.trabajador.numerodocumento)
          sessionStorage.setItem("nombreaprendiz", "")

          if (resp.nivel.tieneevaluacionconocimientos == 'S' && (!resp.eingreso || this.trabajador.eingreso == null || resp.eingreso === 0)) {
            this.notificacionService.displaySuccess('La inscripcion fué exitosa.  Por favor registra el siguiente quiz de conocimientos previos');
            this.router.navigate(['quiz/conocimientosprevios']);
          } else {
            this.notificacionService.displaySuccess('La inscripcion fué exitosa');
          }
        })
      )
      .subscribe();

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

  private _filterLabordesarrollo(value: string): string[] {
    const filterValue = value.toLowerCase();

    return this.laboresdesarrolla.filter(option => option.toLowerCase().includes(filterValue));
  }

  private _filterEmpresa(value: string): Empresa[] {
    const filterValue = value.toLowerCase();
    return this.empresas ? this.empresas.filter(empresa => empresa.nombre.toLowerCase().includes(filterValue)) : [];
  }


  onCheckboxChange(checked: boolean): void {
    this.aceptarterminos = checked;
  }

  onTieneAlergiasChange(event: String) {
    this.tienealergias = event === 'S' ? true : false;
    this.firstFormGroup.controls["alergias"].setValue('');
  }

  onTieneMedicamentosChange(event: String) {
    this.tienemedicamentos = event === 'S' ? true : false;
    this.firstFormGroup.controls["medicamentos"].setValue('');
  }

  onTieneEnfermedadesChange(event: String) {
    this.tieneenfermedades = event === 'S' ? true : false;
    this.firstFormGroup.controls["enfermedades"].setValue('');
  }

  onTieneLesionesChange(event: String) {
    this.tienelesiones = event === 'S' ? true : false;
    this.firstFormGroup.controls["lesiones"].setValue('');
  }

  onTieneDrogasChange(event: String) {
    this.tienedrogas = event === 'S' ? true : false;
    this.firstFormGroup.controls["drogas"].setValue('');
  }

  toggleFormulario() {
    this.mostrardatostrabajador = !this.mostrardatostrabajador;
    this.mostrarformulario = !this.mostrarformulario;
  }
}
