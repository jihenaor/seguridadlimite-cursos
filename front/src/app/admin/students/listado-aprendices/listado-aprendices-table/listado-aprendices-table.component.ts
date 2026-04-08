import { Component, Input } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { Router } from '@angular/router';
import { MatTooltipModule } from '@angular/material/tooltip';
import { utils as XLSXUtils, writeFile } from 'xlsx';

@Component({
    selector: 'app-listado-aprendices-table',
    imports: [
        MatButtonModule,
        MatIconModule,
        MatTooltipModule
    ],
    templateUrl: './listado-aprendices-table.component.html',
    styleUrls: ['./listado-aprendices-table.component.scss']
})
export class ListadoAprendicesTableComponent {
  @Input() data: any[];

  constructor(private router: Router) {}

  irTrabajador(idaprendiz: number) {
    try {
      sessionStorage.setItem('idaprendiz', idaprendiz + '');
      this.router.navigate(['/admin/students/about-aprendiz', idaprendiz]);
    } catch(error) {
      alert('Error al abrir la página del aprendiz' + error);
    }
  }

  exportToExcel(): void {
    try {
      const exportData = this.data.map(aprendiz => ({
        'Tipo Documento': aprendiz.tipoDocumento,
        'Documento': aprendiz.documento,
        'Primer Nombre': aprendiz.primerNombre,
        'Segundo Nombre': aprendiz.segundoNombre,
        'Primer Apellido': aprendiz.primerApellido,
        'Segundo Apellido': aprendiz.segundoApellido,
        'Género': aprendiz.genero,
        'País Nacimiento': aprendiz.paisNacimiento,
        'Fecha Nacimiento': aprendiz.fechaNacimiento,
        'Nivel Educativo': aprendiz.nivelEducativo,
        'Área Trabajo': aprendiz.areaTrabajo,
        'Cargo Actual': aprendiz.cargoActual,
        'Sector': aprendiz.sector,
        'Empleador': aprendiz.empleador,
        'ARL': aprendiz.arl
      }));

      const ws = XLSXUtils.json_to_sheet(exportData);
      const wb = XLSXUtils.book_new();
      XLSXUtils.book_append_sheet(wb, ws, 'Aprendices');

      const fileName = `Aprendices_${new Date().toISOString().split('T')[0]}.xlsx`;
      writeFile(wb, fileName);
    } catch (error) {
      console.error('Error al exportar a Excel:', error);
      alert('Error al exportar a Excel');
    }
  }
}
