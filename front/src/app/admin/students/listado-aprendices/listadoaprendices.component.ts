import { ListadoAprendicesTableComponent } from './listado-aprendices-table/listado-aprendices-table.component';
import { Component, OnInit, ViewChild } from '@angular/core';
import { AprendizExcelService, ImportResultado } from '../services/aprendiz-excel.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { PagetitleComponent } from 'src/app/shared/components/page-title/pagetitle.component';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatDatepicker } from '@angular/material/datepicker';
import { MatIconModule } from '@angular/material/icon';
import { MatDividerModule } from '@angular/material/divider';
import { MatButtonModule } from '@angular/material/button';
import { AprendicesInscritosService } from './listadoaprendices.service';
import { MatSelectModule } from '@angular/material/select';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-listadoaprendices',
  standalone: true,
  imports: [
    PagetitleComponent,
    MatFormFieldModule,
    MatInputModule,
    MatDatepickerModule,
    MatButtonModule,
    MatDividerModule,
    MatIconModule,
    MatSelectModule,
    ListadoAprendicesTableComponent,
    CommonModule,
    FormsModule,
  ],
  templateUrl: './listadoaprendices.component.html',
})
export class ListadoaprendicesComponent implements OnInit {
  startDate: string;
  endDate: string;
  aprendizDetalle: number = -1;
  importando = false;

  @ViewChild('startDatePicker') startDatePicker: MatDatepicker<any>;
  @ViewChild('endDatePicker') endDatePicker: MatDatepicker<any>;

  constructor(
    private excelService: AprendizExcelService,
    private snackBar: MatSnackBar,
    public aprendicesInscritosService: AprendicesInscritosService
  ) {}

  ngOnInit(): void {}

  exportToExcel(): void {
    this.excelService.exportToExcel().subscribe({
      next: (blob: Blob) => {
        const url = window.URL.createObjectURL(blob);
        const link = document.createElement('a');
        link.href = url;
        link.download = 'aprendices.xlsx';
        link.click();
        window.URL.revokeObjectURL(url);
        this.snackBar.open('Archivo Excel descargado correctamente', 'Cerrar', {
          duration: 3000,
          horizontalPosition: 'end',
          verticalPosition: 'top',
        });
      },
      error: () => {
        this.snackBar.open('Error al descargar el archivo Excel', 'Cerrar', {
          duration: 3000,
          horizontalPosition: 'end',
          verticalPosition: 'top',
        });
      },
    });
  }

  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (!input.files || input.files.length === 0) return;

    const file = input.files[0];
    input.value = '';
    this.importando = true;

    this.excelService.importarDesdeExcel(file).subscribe({
      next: (resultado: ImportResultado) => {
        this.importando = false;
        const msg = `Importación completada — Creados: ${resultado.creados} | Omitidos: ${resultado.omitidos} | Errores: ${resultado.errores}`;
        this.snackBar.open(msg, 'Cerrar', {
          duration: 7000,
          horizontalPosition: 'end',
          verticalPosition: 'top',
        });
      },
      error: (err: any) => {
        this.importando = false;
        const mensaje = err?.error?.error ?? 'Error al importar el archivo Excel';
        this.snackBar.open(mensaje, 'Cerrar', {
          duration: 7000,
          horizontalPosition: 'end',
          verticalPosition: 'top',
        });
      },
    });
  }

  consultarAprendices(): void {
    this.aprendicesInscritosService.getPagospendientesempresa(
      this.startDate,
      this.endDate,
      this.aprendizDetalle
    );
  }

  onStartDateChanged(event: any): void {
    this.startDate = this.formatDate(event.value);
  }

  onEndDateChanged(event: any): void {
    this.endDate = this.formatDate(event.value);
  }

  formatDate(date: Date): string {
    const year = date.getFullYear();
    const month = (date.getMonth() + 1).toString().padStart(2, '0');
    const day = date.getDate().toString().padStart(2, '0');
    return `${year}-${month}-${day}`;
  }

  toggleDetail(index: number): void {
    this.aprendizDetalle = this.aprendizDetalle === index ? -1 : index;
  }
}
