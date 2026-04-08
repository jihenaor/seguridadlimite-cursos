import { ListadoAprendicesTableComponent } from './listado-aprendices-table/listado-aprendices-table.component';
import { Component, OnInit, ViewChild } from '@angular/core';
import { AprendizExcelService } from '../services/aprendiz-excel.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { PagetitleComponent } from 'src/app/shared/components/page-title/pagetitle.component';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatDatepicker } from '@angular/material/datepicker';
import {MatIconModule} from '@angular/material/icon';
import {MatDividerModule} from '@angular/material/divider';
import {MatButtonModule} from '@angular/material/button';
import { AprendicesInscritosService } from './listadoaprendices.service';
import { MatSelect, MatSelectModule } from '@angular/material/select';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-listadoaprendices',
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
    FormsModule
  ],
  templateUrl: './listadoaprendices.component.html'
})
export class ListadoaprendicesComponent implements OnInit {
  startDate: String;
  endDate: String;
  aprendizDetalle: number = -1;

  @ViewChild('startDatePicker') startDatePicker: MatDatepicker<any>;
  @ViewChild('endDatePicker') endDatePicker: MatDatepicker<any>;

  constructor(
    private excelService: AprendizExcelService,
    private snackBar: MatSnackBar,
    public aprendicesInscritosService: AprendicesInscritosService) { }

  ngOnInit(): void {
  }

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
          verticalPosition: 'top'
        });
      },
      error: (error) => {
        console.error('Error al exportar a Excel:', error);
        this.snackBar.open('Error al descargar el archivo Excel', 'Cerrar', {
          duration: 3000,
          horizontalPosition: 'end',
          verticalPosition: 'top'
        });
      }
    });
  }

  consultarAprendices() {
    this.aprendicesInscritosService.getPagospendientesempresa(this.startDate, this.endDate, this.aprendizDetalle);
  }

  onStartDateChanged(event: any) {
    const date: Date = event.value;
    this.startDate = this.formatDate(date);
  }

  onEndDateChanged(event: any) {
    const date: Date = event.value;
    this.endDate = this.formatDate(date);
  }

  formatDate(date: Date): string {
    const year: number = date.getFullYear();
    const month: number = date.getMonth() + 1; // Los meses comienzan desde 0
    const day: number = date.getDate();

    // Formatear la fecha como "yyyy-MM-dd"
    const formattedDate: string = `${year}-${month.toString().padStart(2, '0')}-${day.toString().padStart(2, '0')}`;

    return formattedDate;
  }

  toggleDetail(index: number) {
    if (this.aprendizDetalle === index) {
      this.aprendizDetalle = -1; // Oculta el detalle si ya está visible
    } else {
      this.aprendizDetalle = index; // Muestra el detalle de la pregunta seleccionada
    }
  }

}
