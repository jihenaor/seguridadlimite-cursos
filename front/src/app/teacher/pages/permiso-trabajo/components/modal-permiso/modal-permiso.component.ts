import { Component, OnInit, ViewChild, Inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { forkJoin } from 'rxjs';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatIconModule } from '@angular/material/icon';
import { MatTabsModule } from '@angular/material/tabs';
import { MatDividerModule } from '@angular/material/divider';
import { MatListModule, MatSelectionList } from '@angular/material/list';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatRadioModule } from '@angular/material/radio';
import { TimeMaskDirective } from 'src/app/directives/time-mask.directive';
import { FilterByGrupoPipe } from '../../../../../shared/pipes/filter-by-grupo.pipe';

import { NivelsService } from '../../../../../core/service/nivels.service';
import { Nivel } from '../../../../../core/models/nivel.model';
import { PermisoTrabajoAlturasService } from '../../../../../core/service/permiso-trabajo-alturas.service';
import { AuthService } from '../../../../../core/service/auth.service';
import { GrupoChequeo } from '../../../../../core/models/grupo-chequeo.model';
import { InstructorService } from '../../../../../core/service/instructor.service';
import { PermisoDetalleChequeo, PermisoFechas, PermisoTrabajoAlturas } from '../../../../../core/models/permiso-trabajo.interface';
import { PermisoTrabajoService } from '../../../../../core/service/permiso-trabajo.service';
import { AprendizPermisoTrabajoComponent } from '../aprendiz-permiso-trabajo/aprendiz-permiso-trabajo.component';

@Component({
  selector: 'app-modal-permiso',
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    MatDialogModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatCheckboxModule,
    MatIconModule,
    MatTabsModule,
    MatDividerModule,
    MatListModule,
    MatSnackBarModule,
    MatProgressSpinnerModule,
    MatRadioModule,
    TimeMaskDirective,
    FilterByGrupoPipe,
    AprendizPermisoTrabajoComponent
  ],
  templateUrl: './modal-permiso.component.html',
  styleUrls: ['./modal-permiso.component.scss']
})
export class ModalPermisoComponent implements OnInit {
  @ViewChild('tiposTrabajoList') tiposTrabajoList!: MatSelectionList;
  permisoForm!: FormGroup;
  dialogTitle!: string;
  action!: string;
  public permiso!: PermisoTrabajoAlturas;
  data: any;
  niveles: Nivel[] = [];
  guardando = false;
  cargando = true;
  tiposTrabajo: string[] = [
    'Fachada',
    'Estructuras',
    'Poste',
    'Reticula',
    'Cubierta',
    'Suspencion',
    'Andamios',
    'Torre electrica'
  ];

  // Definición de los grupos de chequeo con sus detalles
  gruposChequeo: any[] = [];

  /** Solo Administrador y Coordinador pueden ver/gestionar la pestaña de Responsables */
  get puedeGestionarResponsables(): boolean {
    const role = (this.authService.currentUserValue?.role as string || '').toUpperCase();
    return role === 'ADMINISTRADOR' || role === 'COORDINADOR';
  }

  constructor(
    public dialogRef: MatDialogRef<ModalPermisoComponent>,
    @Inject(MAT_DIALOG_DATA) public dialogData: any,
    private fb: FormBuilder,
    private nivelsService: NivelsService,
    private permisoService: PermisoTrabajoAlturasService,
    private snackBar: MatSnackBar,
    private authService: AuthService,
    public instructorService: InstructorService,
    private permisoTrabajoService: PermisoTrabajoService
  ) {
    this.data = dialogData;
    this.action = dialogData.action;

    this.permiso = {} as PermisoTrabajoAlturas;
    this.permiso.permisoDetalleActividades = [];

    if (this.action === 'edit' || this.action === 'view') {
      this.dialogTitle = this.action === 'edit'
        ? 'Editar Permiso de Trabajo'
        : 'Detalle Permiso de Trabajo';
    } else {
      this.dialogTitle = 'Nuevo Permiso de Trabajo';
      this.permiso.idPersonal = this.authService.currentUserValue.id;
      this.permiso.fechaInicio = new Date() as any;
    }
  }

  ngOnInit(): void {
    this.instructorService.find();

    const permisoId = this.dialogData.permiso?.idPermiso;

    if ((this.action === 'edit' || this.action === 'view') && permisoId) {
      // Cargar niveles y datos del permiso en paralelo, luego inicializar el form
      forkJoin({
        niveles: this.nivelsService.getNivelActivos(),
        permiso: this.permisoTrabajoService.consultarPermiso(permisoId)
      }).subscribe({
        next: ({ niveles, permiso }) => {
          this.niveles = niveles;
          this.inicializarActividades();
          this.crearFormulario();
          this.cargarPermiso(permiso);
          if (this.action === 'view') {
            this.permisoForm.disable();
          }
          this.suscribirCambiosFormulario();
          this.cargando = false;
        },
        error: (err) => {
          console.error('Error al cargar datos del permiso:', err);
          this.snackBar.open('Error al cargar los datos del permiso', 'Cerrar', {
            duration: 3000, horizontalPosition: 'center', verticalPosition: 'bottom'
          });
          this.cargando = false;
        }
      });
    } else {
      // Nuevo permiso: solo cargar niveles
      this.nivelsService.getNivelActivos().subscribe({
        next: (niveles) => {
          this.niveles = niveles;
          this.inicializarActividades();
          this.crearFormulario();
          this.suscribirCambiosFormulario();
          this.cargando = false;
        },
        error: () => {
          this.cargando = false;
        }
      });
    }
  }

  private suscribirCambiosFormulario(): void {
    // Actualizar validación de idpersonaautoriza2 según número de grupos
    this.permisoForm.get('numerogrupos')?.valueChanges.subscribe(value => {
      const ctrl = this.permisoForm.get('idpersonaautoriza2');
      if (value === 2) {
        ctrl?.setValidators([Validators.required]);
      } else {
        ctrl?.clearValidators();
      }
      ctrl?.updateValueAndValidity();
    });
  }

  actualizarGruposChequeo(grupos: GrupoChequeo[]): void {
    // Mapear los grupos de chequeo a la estructura esperada por el componente
    this.gruposChequeo = grupos.map(grupo => {
      const grupoMapeado = {
        idGrupo: grupo.idGrupo,
        nombre: grupo.descripcion,
        codigo: grupo.codigo,
        posiblesValores: grupo.posiblesValores,
        detallesChequeo: grupo.detalles?.map(detalle => {
          return {
            codigoItem: detalle.codigo,
            descripcionItem: detalle.descripcion,
            estado: 'C' // Inicializamos con un valor por defecto
          };
        }) || []
      };

      return grupoMapeado;
    });
  }

  crearFormulario(): void {
    const numeroGrupos = typeof this.permiso?.numerogrupos === 'number' ? this.permiso.numerogrupos : 1;
    const numeroGruposInicial = numeroGrupos >= 1 && numeroGrupos <= 2 ? numeroGrupos : 1;

    this.permisoForm = this.fb.group({
      // Sección A - Información General
      idNivel: [this.permiso.idNivel || null, Validators.required],
      fechaInicio: [this.permiso.fechaInicio || '', Validators.required],
      codigoministerio: [this.permiso.codigoministerio || ''],
      validodesde: [this.permiso.validodesde || ''],
      validohasta: [this.permiso.validohasta || ''],
      cupoinicial: [this.permiso.cupoinicial || ''],
      cupofinal: [this.permiso.cupofinal || ''],
      horaInicio: [this.permiso.horaInicio || ''],
      horaFinal: [this.permiso.horaFinal || ''],
      proyectoAreaSeccion: [this.permiso.proyectoAreaSeccion || '', Validators.required],
      ubicacionEspecifica: [this.permiso.ubicacionEspecifica || '', Validators.required],
      descripcionTarea: [this.permiso.descripcionTarea || '', Validators.required],
      herramientasUtilizar: [this.permiso.herramientasUtilizar || ''],
      alturaTrabajo: [this.permiso.alturaTrabajo || 0],

      // Verificaciones
      verificacionSeguridadSocial: [this.permiso.verificacionSeguridadSocial || false],
      certificadoAptitudMedica: [this.permiso.certificadoAptitudMedica || false],
      certificadoCompetencia: [this.permiso.certificadoCompetencia || false],
      condicionSaludTrabajador: [this.permiso.condicionSaludTrabajador || false],

      idresponsableemeergencias: [this.permiso.idresponsableemeergencias || null],
      idpersonaautoriza1: [this.permiso.idpersonaautoriza1 || null, Validators.required],
      idpersonaautoriza2: [this.permiso.idpersonaautoriza2 || null],
      numerogrupos: [numeroGruposInicial, [
        Validators.required,
        Validators.min(1),
        Validators.max(2)
      ]],
      dias: [this.permiso.dias || 0, [
        Validators.required,
        Validators.min(1),
        Validators.max(20)
      ]],

      // Tipos de trabajo seleccionados
      tiposTrabajoSeleccionados: [this.obtenerTiposTrabajoSeleccionados() || []]
    });
  }

  obtenerTiposTrabajoSeleccionados(): string[] {
    if (!this.permiso.tiposTrabajo) return [];
    return this.permiso.tiposTrabajo.map(tipo => tipo.tipoTrabajo);
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  /** Serializa un valor Date|string a 'YYYY-MM-DD' para el backend. */
  private serializeDate(value: Date | string | null | undefined): string | null {
    if (!value) return null;
    if (value instanceof Date) return this.formatearFecha(value);
    return value as string;
  }

  guardarPermiso(): void {
    if (this.permisoForm.invalid) {
      this.mostrarCamposInvalidos();
      return;
    }

    this.guardando = true;
    const rawValues = this.permisoForm.value;
    const formValues = {
      ...rawValues,
      fechaInicio: this.serializeDate(rawValues.fechaInicio),
      validodesde: this.serializeDate(rawValues.validodesde),
      validohasta: this.serializeDate(rawValues.validohasta),
    };

    // Obtener los tipos de trabajo seleccionados de forma segura
    let tiposSeleccionados: string[] = [];
    if (this.tiposTrabajoList && this.tiposTrabajoList.selectedOptions) {
      tiposSeleccionados = this.tiposTrabajoList.selectedOptions.selected.map(option => option.value);
    } else {
      tiposSeleccionados = this.permisoForm.get('tiposTrabajoSeleccionados')?.value || [];
    }

    // Actualizar this.permiso con los datos del formulario
    this.permiso = {
      ...this.permiso,
      ...formValues,
      tiposTrabajo: tiposSeleccionados.map(tipo => ({
        id: 0,
        idPermiso: this.permiso.idPermiso,
        tipoTrabajo: tipo
      }))
    };

    this.permisoService.actualizarPermiso(this.permiso.idPermiso, this.permiso)
      .subscribe({
        next: (response) => {
          this.mostrarMensaje('Permiso actualizado correctamente');
          this.dialogRef.close(response);
        },
        error: (error) => {
          console.error('Error al actualizar el permiso:', error);
          this.mostrarMensaje('Error al actualizar el permiso');
          this.guardando = false;
        }
      });
  }

  mostrarCamposInvalidos(): void {
    if (!this.permisoForm) {
      this.snackBar.open('El formulario no está inicializado correctamente', 'Cerrar', {
        duration: 3000,
        horizontalPosition: 'center',
        verticalPosition: 'bottom',
        panelClass: ['error-snackbar']
      });
      return;
    }

    const controls = this.permisoForm.controls;
    const camposInvalidos = [];

    for (const name in controls) {
      if (controls[name].invalid) {
        const control = controls[name];
        let mensaje = '';

        if (control.hasError('required')) {
          mensaje = 'es requerido';
        } else if (control.hasError('minlength')) {
          mensaje = `debe tener al menos ${control.errors?.['minlength'].requiredLength} caracteres`;
        } else if (control.hasError('maxlength')) {
          mensaje = `no debe exceder ${control.errors?.['maxlength'].requiredLength} caracteres`;
        } else if (control.hasError('pattern')) {
          mensaje = 'no cumple con el formato requerido';
        }

        camposInvalidos.push(`${name}: ${mensaje}`);
      }
    }

    if (camposInvalidos.length > 0) {
      this.snackBar.open(
        `Campos con error:\n${camposInvalidos.join('\n')}`,
        'Cerrar',
        {
          duration: 5000,
          horizontalPosition: 'center',
          verticalPosition: 'bottom',
          panelClass: ['error-snackbar']
        }
      );
    } else {
      this.snackBar.open('No hay campos inválidos', 'Cerrar', {
        duration: 3000,
        horizontalPosition: 'center',
        verticalPosition: 'bottom'
      });
    }
  }

  obtenerDetallesChequeo(): PermisoDetalleChequeo[] {
    const detallesChequeo: PermisoDetalleChequeo[] = [];

    // Recorrer todos los grupos de chequeo
    this.gruposChequeo.forEach(grupo => {
      // Recorrer los detalles de cada grupo
      if (grupo.detallesChequeo && grupo.detallesChequeo.length > 0) {
        grupo.detallesChequeo.forEach(detalle => {
          // Solo agregar los detalles que tienen un estado seleccionado
          if (detalle.estado) {
            /*
            detallesChequeo.push({
              idGrupo: grupo.idGrupo,
              idPermiso: this.permiso.idPermiso || 0,
              descripcion: detalle.descripcionItem,
              respuesta: detalle.estado
            });
            */
          }
        });
      }
    });

    return detallesChequeo;
  }

  // Método para mapear los detalles de chequeo del permiso a la estructura del formulario
  mapearDetallesChequeoAFormulario(detallesChequeo: PermisoDetalleChequeo[]): void {
    if (!detallesChequeo || detallesChequeo.length === 0) {
      return;
    }
    /*
        detallesChequeo.forEach(detalle => {
          // Buscar el grupo correspondiente
          const grupo = this.gruposChequeo.find(g => g.idGrupo === detalle.idGrupo);
          if (grupo) {
            // Buscar
            /*el detalle correspondiente
            const detalleEncontrado = grupo.detallesChequeo.find(d =>
              d.descripcionItem === detalle.descripcion
            );
    
            if (detalleEncontrado) {
              // Asignar la respuesta según el valor de respuesta
              detalleEncontrado.estado = detalle.respuesta || 'N';
            }
           }
        });
    */
  }

  mostrarMensaje(mensaje: string): void {
    this.snackBar.open(mensaje, 'Cerrar', {
      duration: 3000,
      horizontalPosition: 'center',
      verticalPosition: 'bottom'
    });
  }

  /** Convierte un string 'YYYY-MM-DD' a Date para los MatDatepicker. */
  private parseDate(value: string | null | undefined): Date | null {
    if (!value) return null;
    const d = new Date(value + 'T00:00:00');
    return isNaN(d.getTime()) ? null : d;
  }

  cargarPermiso(permiso: PermisoTrabajoAlturas): void {
    if (!permiso) return;

    // Asegurar que numerogrupos sea un número válido
    const numeroGrupos = typeof permiso.numerogrupos === 'number' ? permiso.numerogrupos : 1;

    this.permiso = {
      ...permiso,
      numerogrupos: numeroGrupos >= 1 && numeroGrupos <= 2 ? numeroGrupos : 1
    };

    // Cargar los datos del permiso en el formulario
    this.permisoForm.patchValue({
      idNivel: permiso.idNivel,
      fechaInicio: this.parseDate(permiso.fechaInicio),
      validodesde: this.parseDate(permiso.validodesde),
      validohasta: this.parseDate(permiso.validohasta),
      codigoministerio: permiso.codigoministerio,
      horaInicio: permiso.horaInicio || '',
      horaFinal: permiso.horaFinal || '',
      cupoinicial: permiso.cupoinicial,
      cupofinal: permiso.cupofinal,
      proyectoAreaSeccion: permiso.proyectoAreaSeccion,
      ubicacionEspecifica: permiso.ubicacionEspecifica,
      descripcionTarea: permiso.descripcionTarea,
      herramientasUtilizar: permiso.herramientasUtilizar,
      alturaTrabajo: permiso.alturaTrabajo,
      verificacionSeguridadSocial: permiso.verificacionSeguridadSocial,
      certificadoAptitudMedica: permiso.certificadoAptitudMedica,
      certificadoCompetencia: permiso.certificadoCompetencia,
      condicionSaludTrabajador: permiso.condicionSaludTrabajador,
      idpersonaautoriza1: permiso.idpersonaautoriza1,
      idpersonaautoriza2: permiso.idpersonaautoriza2,
      idresponsableemeergencias: permiso.idresponsableemeergencias,
      numerogrupos: permiso.numerogrupos,
      dias: permiso.dias
    });

    // Actualizar validación de idpersonaautoriza2 según el número de grupos
    const idpersonaautoriza2Control = this.permisoForm.get('idpersonaautoriza2');
    if (this.permiso.numerogrupos === 2) {
      idpersonaautoriza2Control?.setValidators([Validators.required]);
    } else {
      idpersonaautoriza2Control?.clearValidators();
    }
    idpersonaautoriza2Control?.updateValueAndValidity();

    // Mapear los detalles de chequeo a la estructura del formulario
    if (permiso.permisoDetalleChequeos && permiso.permisoDetalleChequeos.length > 0) {
      this.mapearDetallesChequeoAFormulario(permiso.permisoDetalleChequeos);
    }

    // Inicializar los tipos de trabajo seleccionados
    if (permiso.tiposTrabajo && permiso.tiposTrabajo.length > 0) {
      const tiposSeleccionados = permiso.tiposTrabajo.map(t => t.tipoTrabajo);
      this.permisoForm.patchValue({
        tiposTrabajoSeleccionados: tiposSeleccionados
      });
    }
  }

  agregarActividad(): void {
    this.permiso.permisoDetalleActividades = [
      ...(this.permiso.permisoDetalleActividades || []),
      {
        idPermisoActividad: 0,
        idPermiso: this.permiso.idPermiso,
        actividadrealizar: '',
        peligros: '',
        controlesrequeridos: ''
      }
    ];
  }

  actualizarActividad(index: number, campo: string, valor: string): void {
    if (this.permiso.permisoDetalleActividades && this.permiso.permisoDetalleActividades[index]) {
      this.permiso.permisoDetalleActividades[index] = {
        ...this.permiso.permisoDetalleActividades[index],
        [campo]: valor
      };
    }
  }

  eliminarActividad(index: number): void {
    if (this.permiso?.permisoDetalleActividades) {
      this.permiso.permisoDetalleActividades.splice(index, 1);
    }
  }

  inicializarActividades(): void {
    this.permiso.permisoDetalleActividades = [];
  }

  consultarPermiso(id: number): void {
    this.cargando = true;
    this.permisoTrabajoService.consultarPermiso(id).subscribe({
      next: (permisoConsulta) => {
        if (permisoConsulta) {

          // Cargar los datos en el formulario
          this.cargarPermiso(permisoConsulta);
        }
        this.cargando = false;
      },
      error: (error) => {
        console.error('Error al consultar el permiso:', error);
        this.snackBar.open('Error al cargar los datos del permiso', 'Cerrar', {
          duration: 3000,
          horizontalPosition: 'center',
          verticalPosition: 'bottom'
        });
        this.cargando = false;
      }
    });
  }

  validarCamposVisibles(): void {
    // Reservado — la validación se muestra inline en los mat-error del template
  }

  generarCalendario(): void {
    const validodesde = this.permisoForm.get('validodesde')?.value;
    const validohasta = this.permisoForm.get('validohasta')?.value;
    const dias = this.permisoForm.get('dias')?.value;

    if (!validodesde || !validohasta || !dias) {
      alert('Debe especificar la fecha de inicio, fecha hasta y el número de días');
      return;
    }

    // Limpiar fechas existentes
    this.permiso.permisoFechas = [];

    if (dias === 1) {
      // Si solo hay un día, usar la fecha de inicio
      const permisoFecha: PermisoFechas = {
        idPermiso: this.permiso.idPermiso || 0,
        fecha: validodesde,
        dia: 1
      };
      this.permiso.permisoFechas.push(permisoFecha);
    } else if (dias === 2) {
      // Si hay dos días, usar fecha inicio y fecha hasta
      const permisoFechaInicio: PermisoFechas = {
        idPermiso: this.permiso.idPermiso || 0,
        fecha: validodesde,
        dia: 1
      };
      const permisoFechaFin: PermisoFechas = {
        idPermiso: this.permiso.idPermiso || 0,
        fecha: validohasta,
        dia: 2
      };
      this.permiso.permisoFechas.push(permisoFechaInicio, permisoFechaFin);
    } else {
      // Más de 2 días: primer día = fechaInicio, último día = fechaHasta, intermedios calculados
      const fechaInicioDate = new Date(validodesde + 'T00:00:00');
      const fechaHastaDate = new Date(validohasta + 'T00:00:00');
      
      // Calcular el intervalo entre fechas para distribuir los días intermedios
      const diferenciaDias = Math.floor((fechaHastaDate.getTime() - fechaInicioDate.getTime()) / (1000 * 60 * 60 * 24));
      const intervaloDias = diferenciaDias > 1 ? Math.floor(diferenciaDias / (dias - 1)) : 1;

      for (let i = 0; i < dias; i++) {
        let fechaString: string;
        
        if (i === 0) {
          // Primer registro: usar fechaInicio
          fechaString = validodesde;
        } else if (i === dias - 1) {
          // Último registro: usar fechaHasta
          fechaString = validohasta;
        } else {
          // Registros intermedios: calcular fecha intermedia
          const fechaIntermedia = new Date(fechaInicioDate);
          fechaIntermedia.setDate(fechaInicioDate.getDate() + (i * intervaloDias));
          fechaString = this.formatearFecha(fechaIntermedia);
        }

        const permisoFecha: PermisoFechas = {
          idPermiso: this.permiso.idPermiso || 0,
          fecha: fechaString,
          dia: i + 1
        };

        this.permiso.permisoFechas.push(permisoFecha);
      }
    }
  }

  // Método para limpiar el calendario
  limpiarCalendario(): void {
    if (confirm('¿Está seguro de que desea limpiar todas las fechas del calendario?')) {
      this.permiso.permisoFechas = [];
    }
  }

  // Método para eliminar una fecha específica
  eliminarFechaCalendario(index: number): void {
    if (confirm('¿Está seguro de que desea eliminar esta fecha?')) {
      this.permiso.permisoFechas.splice(index, 1);
    }
  }

  // Método para obtener el día de la semana
  getDiaSemana(fecha: string): string {
    const diasSemana = ['Domingo', 'Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes', 'Sábado'];
    const fechaDate = new Date(fecha + 'T00:00:00');
    return diasSemana[fechaDate.getDay()];
  }

  // Método para formatear fecha como YYYY-MM-DD
  formatearFecha(date: Date): string {
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
  }

  // Método para actualizar fecha editable
  actualizarFechaCalendario(index: number, event: any): void {
    const nuevaFecha = event.target.value;
    if (nuevaFecha && this.isValidDate(nuevaFecha)) {
      this.permiso.permisoFechas[index].fecha = nuevaFecha;
    }
  }

  // Método para validar formato de fecha YYYY-MM-DD
  isValidDate(dateString: string): boolean {
    const regex = /^\d{4}-\d{2}-\d{2}$/;
    if (!regex.test(dateString)) return false;
    
    const date = new Date(dateString + 'T00:00:00');
    return date instanceof Date && !isNaN(date.getTime());
  }

  // Método para verificar si una fecha es editable (no es primera ni última)
  esFechaEditable(index: number): boolean {
    const totalFechas = this.permiso.permisoFechas?.length || 0;
    return totalFechas > 2 && index > 0 && index < totalFechas - 1;
  }
}
