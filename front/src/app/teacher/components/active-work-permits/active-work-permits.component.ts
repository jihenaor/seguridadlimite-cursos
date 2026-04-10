import { Component, OnInit, effect } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PermisoTrabajoSignalsService } from '../../../core/service/permiso-trabajo-signals.service';
import { MatTableModule } from '@angular/material/table';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { FormsModule } from '@angular/forms';
import { MatIconModule } from '@angular/material/icon';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { PermisoTrabajoAlturas } from '../../../core/models/permiso-trabajo.interface';

@Component({
  selector: 'app-active-work-permits',
  templateUrl: './active-work-permits.component.html',
  styleUrls: ['./active-work-permits.component.css'],
  standalone: true,
  imports: [
    CommonModule,
    MatTableModule,
    MatFormFieldModule,
    MatInputModule,
    MatDatepickerModule,
    MatNativeDateModule,
    FormsModule,
    MatIconModule,
    MatCheckboxModule
  ]
})
export class ActiveWorkPermitsComponent implements OnInit {
  selectedDate: Date;
  displayedColumns: string[] = [
    'expand',
    'seleccionado',
    'nombrenivel',
    'validodesde', 
    'validohasta', 
    'acciones'];
  
  // Control de filas expandidas
  expandedPermiso: PermisoTrabajoAlturas | null = null;

  constructor(public permisoTrabajoService: PermisoTrabajoSignalsService) {
    this.selectedDate = new Date();
    effect(() => {
      const permisos = this.permisoTrabajoService.permisos();
      if (permisos && permisos.length > 0) {
        this.initializePermisos();
      }
    });
  }

  ngOnInit() {
    this.loadActivePermits();
  }

  onDateChange(event: any): void {
    this.loadActivePermits();
  }

  loadActivePermits() {
    const year = this.selectedDate.getFullYear();
    const month = String(this.selectedDate.getMonth() + 1).padStart(2, '0');
    const day = String(this.selectedDate.getDate()).padStart(2, '0');
    const formattedDate = `${year}-${month}-${day}`;
    this.permisoTrabajoService.consultarPorFecha(formattedDate);
  }

  initializePermisos() {
    try {
      const permisos = this.permisoTrabajoService.permisos();
      if (!Array.isArray(permisos)) {
        console.error('Los permisos no son un array');
        return;
      }

      permisos.forEach(permiso => {
        if (!permiso || typeof permiso !== 'object') {
          console.error('Permiso inválido:', permiso);
          return;
        }
        
        if (permiso.seleccionado === undefined) {
          permiso.seleccionado = false;
        }
      });
    } catch (error) {
      console.error('Error al inicializar permisos:', error);
    }
  }

  onSelectionChange(permiso: PermisoTrabajoAlturas) {
    permiso.seleccionado = !permiso.seleccionado;
  }

  updatePermiso(permiso: PermisoTrabajoAlturas) {
    console.log('Updating permiso:', permiso);
    // Implement update logic here
  }

  // Método para expandir/contraer filas
  toggleExpand(permiso: PermisoTrabajoAlturas) {
    this.expandedPermiso = this.expandedPermiso === permiso ? null : permiso;
  }

  // Verificar si una fila está expandida
  isExpanded(permiso: PermisoTrabajoAlturas): boolean {
    return this.expandedPermiso === permiso;
  }

  // Obtener el día de la semana en español
  getDiaSemana(fecha: string): string {
    const date = new Date(fecha + 'T00:00:00');
    const dias = ['Domingo', 'Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes', 'Sábado'];
    return dias[date.getDay()];
  }
}
