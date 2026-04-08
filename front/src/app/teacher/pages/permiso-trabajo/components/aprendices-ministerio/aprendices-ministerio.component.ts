import { Component, Input, OnInit, Inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatCardModule } from '@angular/material/card';
import { MatDialogModule, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { GruposministerioService } from '../../../../../core/service/gruposministerio.service';
import { InformeMinisterio } from '../../../../../core/models/informe-ministerio.model';
import * as XLSX from 'xlsx';

@Component({
  selector: 'app-aprendices-ministerio',
  templateUrl: './aprendices-ministerio.component.html',
  styleUrls: ['./aprendices-ministerio.component.scss'],
  standalone: true,
  imports: [
    CommonModule,
    MatTableModule,
    MatButtonModule,
    MatIconModule,
    MatProgressSpinnerModule,
    MatCardModule,
    MatDialogModule
  ],
  providers: [GruposministerioService]
})
export class AprendicesMinisterioComponent implements OnInit {
  @Input() selectedIds: number[] = [];
  
  datosMinisterio: InformeMinisterio[] = [];
  cargando = false;
  error = '';
  mostrarDatos = false;

  displayedColumns: string[] = [
    'entrenadorNombrecompleto',
    'supervisorNombrecompleto',
    'fechainicio',
    'fechafinal',
    'codigoministerio',
    'trabajadorNombrecompleto',
    'trabajadorNumerodocumento',
    'aprendizCodigoverificacion'
  ];

  constructor(
    private gruposministerioService: GruposministerioService,
    public dialogRef: MatDialogRef<AprendicesMinisterioComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { selectedIds: number[] }
  ) {}

  ngOnInit(): void {
    // Use data from dialog injection if available, otherwise use Input
    const idsToUse = this.data?.selectedIds || this.selectedIds;
    if (idsToUse.length > 0) {
      this.selectedIds = idsToUse;
      this.mostrarDatos = true;
      this.consultarDatos();
    }
  }

  consultarDatos(): void {
    if (this.selectedIds.length === 0) {
      this.error = 'No hay IDs seleccionados para consultar';
      return;
    }

    this.cargando = true;
    this.error = '';
    this.mostrarDatos = true;

    this.gruposministerioService.consultarDatosMinisterio(this.selectedIds).subscribe({
      next: (data) => {
        this.datosMinisterio = data;
        this.cargando = false;
      },
      error: (error) => {
        this.error = 'Error al consultar los datos del ministerio: ' + error.message;
        this.cargando = false;
        this.datosMinisterio = [];
      }
    });
  }

  exportarAExcel(): void {
    if (this.datosMinisterio.length === 0) {
      return;
    }

    // Preparar datos para Excel
    const datosExcel = this.datosMinisterio.map(item => ({
      'Entrenador': item.entrenadorNombrecompleto,
      'Supervisor': item.supervisorNombrecompleto,
      'Fecha Inicio': item.fechainicio,
      'Fecha Final': item.fechafinal,
      'Código Ministerio': item.codigoministerio,
      'Trabajador': item.trabajadorNombrecompleto,
      'Documento': item.trabajadorNumerodocumento,
      'Área Trabajo': item.trabajadorAreatrabajo,
      'Nivel Educativo': item.trabajadorNiveleducativo,
      'Cargo Actual': item.trabajadorCargoactual,
      'Género': item.trabajadorGenero,
      'Nacionalidad': item.trabajadorNacionalidad,
      'Código Verificación': item.aprendizCodigoverificacion
    }));

    // Crear libro de trabajo
    const ws: XLSX.WorkSheet = XLSX.utils.json_to_sheet(datosExcel);
    const wb: XLSX.WorkBook = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(wb, ws, 'Aprendices Ministerio');

    // Generar nombre de archivo con fecha
    const fecha = new Date().toISOString().split('T')[0];
    const nombreArchivo = `aprendices_ministerio_${fecha}.xlsx`;

    // Descargar archivo
    XLSX.writeFile(wb, nombreArchivo);
  }

  limpiarDatos(): void {
    this.datosMinisterio = [];
    this.mostrarDatos = false;
    this.error = '';
  }
}
