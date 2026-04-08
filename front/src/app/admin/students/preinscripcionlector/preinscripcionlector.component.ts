import { Component, OnInit, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { PreinscripcionlectorService } from './preinscripcionlector.service';
import { TrabajadorInscripcion } from '../../../core/models/trabajador-inscripcion.model';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatTableModule } from '@angular/material/table';
import { ServicesService } from '../../../core/service/services.service';
import { NivelsService } from '../../../core/service/nivels.service';
import { Nivel } from '../../../core/models/nivel.model';
import { SaveTrabajadorAprendizService } from '../../../core/service/savetrabajadoraprendiz.service';
import { Empresa } from '../../../core/models/empresa.model';
import { FormsModule } from '@angular/forms';
import { MatSelectModule } from '@angular/material/select';
import { ElementRef } from '@angular/core';

interface PersonData {
  documento: string;
  apellido1: string;
  apellido2: string;
  nombres: string;
  sexo: string;
  fechaNacimiento: string;
  rh: string;
}

@Component({
  selector: 'app-preinscripcionlector',
  templateUrl: './preinscripcionlector.component.html',
  styleUrls: ['./preinscripcionlector.component.scss'],
  standalone: true,
  imports: [
    CommonModule,
    MatButtonModule,
    MatIconModule,
    MatFormFieldModule,
    MatInputModule,
    MatCardModule,
    MatSnackBarModule,
    MatProgressSpinnerModule,
    MatTableModule,
    MatSelectModule,
    FormsModule,
    ReactiveFormsModule
  ]
})
export class PreinscripcionlectorComponent implements OnInit {
  @ViewChild('documento') documentoInput!: ElementRef;
  @ViewChild('apellido1') apellido1Input!: ElementRef;
  @ViewChild('apellido2') apellido2Input!: ElementRef;
  @ViewChild('nombres') nombresInput!: ElementRef;
  @ViewChild('sexo') sexoInput!: ElementRef;
  @ViewChild('fechaNacimiento') fechaNacimientoInput!: ElementRef;
  @ViewChild('rh') rhInput!: ElementRef;

  form: FormGroup;
  personData: PersonData | null = null;
  trabajadorData: TrabajadorInscripcion | null = null;
  empresas: Empresa[] = [];
  niveles: Nivel[] = [];
  loading = false;
  displayedColumns: string[] = ['id', 'nombre', 'actions'];
  displayedColumnsNiveles: string[] = ['id', 'nombre', 'descripcion', 'actions'];
  selectedEmpresa: Empresa | null = null;
  selectedNivel: Nivel | null = null;
  isBarcodeScan = false;
  lastKeyTime = 0;
  fieldOrder = ['documento', 'apellido1', 'apellido2', 'nombres', 'sexo', 'fechaNacimiento', 'rh'];

  constructor(
    private fb: FormBuilder,
    private service: PreinscripcionlectorService,
    private services: ServicesService,
    private snackBar: MatSnackBar,
    private nivelesServicio: NivelsService,
    private saveTrabajadorAprendizService: SaveTrabajadorAprendizService,
  ) {
    this.form = this.fb.group({
      documento: ['', Validators.required],
      apellido1: ['', Validators.required],
      apellido2: [''],
      nombres: ['', Validators.required],
      sexo: ['', Validators.required],
      fechaNacimiento: ['', Validators.required],
      rh: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.focusInput();
    this.cargarEmpresas();
    this.cargarNiveles();
  }

  private focusInput(): void {
    setTimeout(() => {
      const input = this.documentoInput.nativeElement;
      if (input) input.focus();
    }, 0);
  }

  handleKeyDown(event: KeyboardEvent, field: string): void {
    const currentTime = event.timeStamp;
    this.isBarcodeScan = (currentTime - this.lastKeyTime < 50);
    this.lastKeyTime = currentTime;

    if (event.key === 'Tab' || event.key === 'Enter') {
      event.preventDefault();
      
      const currentIndex = this.fieldOrder.indexOf(field);
      if (currentIndex < this.fieldOrder.length - 1) {
        // Move to next field
        const nextField = this.fieldOrder[currentIndex + 1];
        const nextInput = this[`${nextField}Input`].nativeElement;
        nextInput.focus();
      } else {
        // Last field, submit form
        this.onBarcodeSubmit();
      }
    }
  }

  onBarcodeSubmit(): void {
    if (this.form.valid) {
      const formValue = this.form.value;
      
      // Format date from DDMMYYYY to YYYY-MM-DD
      const fechaNac = formValue.fechaNacimiento;
      const dia = fechaNac.substr(0,2);
      const mes = fechaNac.substr(2,2);
      const anio = fechaNac.substr(4,4);
      const formattedDate = `${anio}-${mes}-${dia}`;

      // Asegurar que todos los campos tengan valores válidos
      this.personData = {
        documento: formValue.documento?.trim() || '',
        apellido1: formValue.apellido1?.trim() || '',
        apellido2: formValue.apellido2?.trim() || '',
        nombres: formValue.nombres?.trim() || '',
        sexo: formValue.sexo?.trim().toUpperCase() || '',
        fechaNacimiento: formattedDate,
        rh: formValue.rh?.trim().toUpperCase() || ''
      };

      // Validar que los campos requeridos tengan contenido
      if (!this.personData.documento || !this.personData.apellido1 || 
          !this.personData.nombres || !this.personData.sexo || 
          !this.personData.fechaNacimiento || !this.personData.rh) {
        this.snackBar.open('Por favor complete todos los campos requeridos', 'Cerrar', {
          duration: 3000,
          horizontalPosition: 'end',
          verticalPosition: 'top'
        });
        return;
      }

      this.loading = true;
      this.service.getTrabajadorInscripcion(this.personData.documento)
        .subscribe({
          next: (response) => {
            this.trabajadorData = response;
            this.snackBar.open('Datos cargados correctamente', 'Cerrar', {
              duration: 3000,
              horizontalPosition: 'end',
              verticalPosition: 'top'
            });
          },
          error: (error) => {
            console.error('Error fetching data:', error);
            this.snackBar.open('Error al cargar los datos', 'Cerrar', {
              duration: 3000,
              horizontalPosition: 'end',
              verticalPosition: 'top'
            });
          },
          complete: () => {
            this.loading = false;
            this.form.reset();
            this.documentoInput.nativeElement.focus();
          }
        });
    } else {
      this.snackBar.open('Por favor complete el formulario correctamente', 'Cerrar', {
        duration: 3000,
        horizontalPosition: 'end',
        verticalPosition: 'top'
      });
    }
  }

  selectEmpresa(empresa: Empresa): void {
    this.selectedEmpresa = empresa;
    this.snackBar.open(`Empresa seleccionada: ${empresa.nombre}`, 'Cerrar', {
      duration: 3000
    });
  }

  onNivelChange(event: any) {
    this.selectedNivel = this.niveles.find(nivel => nivel.id === event.value) || null;
  }

  async cargarEmpresas(): Promise<void> {
    try {
      this.empresas = await this.services.executeFetch('/empresasseleccionadas');
      if (this.empresas.length === 1) {
        this.selectedEmpresa = this.empresas[0];
      }
    } catch (error) {
      console.error('Error loading empresas:', error);
      this.snackBar.open('Error al cargar empresas', 'Cerrar', {
        duration: 3000
      });
    }
  }

  cargarNiveles(): void {
    this.nivelesServicio.getNivelActivosFecha().subscribe(
      (data) => {
        this.niveles = data;
      },
      (error) => {
        console.error('Error al cargar niveles:', error);
        this.snackBar.open('Error al cargar niveles', 'Cerrar', {
          duration: 3000
        });
      }
    );
  }

  async save() {
    if (!this.personData || !this.selectedEmpresa || !this.selectedNivel) {
      this.snackBar.open('Por favor seleccione una empresa y un nivel', 'Cerrar', {
        duration: 3000,
        horizontalPosition: 'end',
        verticalPosition: 'top'
      });
      return;
    }
  
    const trabajadorInscripcion: TrabajadorInscripcion = {
      // Basic info from barcode
      numerodocumento: this.personData.documento,
      primernombre: this.personData.nombres,
      segundonombre: '',
      primerapellido: this.personData.apellido1,
      segundoapellido: this.personData.apellido2 || '',
      genero: this.personData.sexo,
      fechanacimiento: this.personData.fechaNacimiento,
      tiposangre: this.personData.rh,
      
      // Selected data
      idempresa: this.selectedEmpresa.id,
      empresa: this.selectedEmpresa.nombre,
      nit: this.selectedEmpresa.numerodocumento,
      idnivel: this.selectedNivel.id,
      nombrenivel: this.selectedNivel.nombre,
      
      // Additional required fields
      tipodocumento: 'CC',
      inscripcionconscaner: 'S',
      valido: 'S',
      aprendizContinuaAprendizaje: this.trabajadorData?.aprendizContinuaAprendizaje || false,
      
      // Initialize other required fields
      id: this.trabajadorData ? this.trabajadorData.id : null,
      nacionalidad: 'CO',

      // Required fields from interface
      exception: '',
      idaprendiz: this.trabajadorData ? this.trabajadorData.idaprendiz : 0,
      idenfasis: this.selectedEmpresa.idenfasis,
      inscripcionporlector: true,
    };
  
    try {
      this.loading = true;
      const response = await this.saveTrabajadorAprendizService.save(trabajadorInscripcion).toPromise();
      
      this.snackBar.open('Inscripción exitosa', 'Cerrar', {
        duration: 3000,
        horizontalPosition: 'end',
        verticalPosition: 'top'
      });
  
  
    } catch (error) {
      console.error('Error saving inscription:', error);
      this.snackBar.open('Error al guardar la inscripción', 'Cerrar', {
        duration: 3000
      });
    } finally {
      this.loading = false;
    }
  }

}
