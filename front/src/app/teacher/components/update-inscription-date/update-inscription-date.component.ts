import { Component, EffectRef, effect, inject, signal } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatNativeDateModule } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';

import { Nivel } from '../../../core/models/nivel.model';
import { IniciarPermisoTrabajoAlturasService } from '../../../core/service/iniciarPermisoTrabajoAlturas.service';
import { ActiveWorkPermitsComponent } from '../active-work-permits/active-work-permits.component';
import { MatTabsModule } from '@angular/material/tabs';

@Component({
  selector: 'app-update-inscription-date',
  standalone: true,
  imports: [
    CommonModule,
    MatInputModule,
    MatButtonModule,
    FormsModule,
    MatCheckboxModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatFormFieldModule,
    MatDialogModule,
    MatIconModule,
    MatTabsModule,
    ActiveWorkPermitsComponent
  ],
  styles: [`
    .addContainer {
      max-width: 100%;
      width: 100%;
    }
    .modalHeader {
      display: flex;
      justify-content: space-between;
      align-items: center;
      padding: 10px 20px;
      background: #f5f5f5;
      border-bottom: 1px solid #ddd;
    }
    .modal-about {
      font-size: 1.1rem;
      font-weight: 500;
    }
    .mat-dialog-content {
      padding: 20px;
      margin: 0;
    }
    .row {
      margin-right: -12px;
      margin-left: -12px;
    }
    .col-xl-12, .col-lg-12, .col-md-12, .col-sm-12 {
      padding-right: 12px;
      padding-left: 12px;
    }
    .mt-3 {
      margin-top: 1rem;
    }
    .mt-4 {
      margin-top: 1.5rem;
    }
    .mb-2 {
      margin-bottom: 0.5rem;
    }
    .me-2 {
      margin-right: 0.5rem;
    }
  `],

  templateUrl: './update-inscription-date.component.html'
})
export class UpdateInscriptionDateComponent {
  niveles: Nivel[] = [];
  updateSuccess = signal(false);
  private effectRef: EffectRef | undefined;

  dialogRef = inject(MatDialogRef<UpdateInscriptionDateComponent>);
  private iniciarPermisoTrabajoAlturasService = inject(IniciarPermisoTrabajoAlturasService);
  private data = inject(MAT_DIALOG_DATA) as { niveles: Nivel[] };

  constructor() {
    if (this.data && this.data.niveles) {
      this.niveles = this.data.niveles.map(nivel => ({
        ...nivel,
        seleccionado: false
      }));
    }
    this.setupEffect();
  }

  onSelectionChange(nivel: Nivel) {
  }

  formatDate(date: string | Date): string {
    if (!date) return '';
    if (typeof date === 'string') return date;
    
    const d = new Date(date);
    const year = d.getFullYear();
    const month = String(d.getMonth() + 1).padStart(2, '0');
    const day = String(d.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
  }

  onDateChange(nivel: Nivel, field: 'fechadesde' | 'fechahasta' , event: any) {
    // For HTML5 date input, the value is already in YYYY-MM-DD format
    const dateValue = event.target.value;
    if (dateValue) {
      // Ensure the date is stored as YYYY-MM-DD string
      nivel[field] = dateValue;
      console.log(`${field} set to:`, dateValue);
      console.log('Date format verified:', /^\d{4}-\d{2}-\d{2}$/.test(dateValue));
      console.log('Full nivel object:', nivel);
    }
  }

  formatDateInput(event: any, field: 'fechadesde' | 'fechahasta', nivel: Nivel) {
    let value = event.target.value.replace(/\D/g, ''); // Remove non-digits
    
    // Format as YYYY-MM-DD
    if (value.length >= 4) {
      value = value.substring(0, 4) + '-' + value.substring(4);
    }
    if (value.length >= 7) {
      value = value.substring(0, 7) + '-' + value.substring(7, 9);
    }
    
    // Limit to 10 characters (YYYY-MM-DD)
    if (value.length > 10) {
      value = value.substring(0, 10);
    }
    
    // Update the input value
    event.target.value = value;
    
    // Update the model if it's a valid date format
    if (value.length === 10 && this.isValidDate(value)) {
      nivel[field] = value;
    }
  }

  isValidDate(dateString: string): boolean {
    const regex = /^\d{4}-\d{2}-\d{2}$/;
    if (!regex.test(dateString)) return false;
    
    const [year, month, day] = dateString.split('-').map(Number);
    const date = new Date(year, month - 1, day);
    
    return date.getFullYear() === year &&
           date.getMonth() === month - 1 &&
           date.getDate() === day;
  }

  private setupEffect() {
    this.effectRef = effect(() => {
      if (this.updateSuccess()) {
        alert('Fechas actualizadas correctamente');
        this.dialogRef.close(true);
      }
    });
  }

  validateDate(date: string | undefined): boolean {
    if (!date) return false;
    const dateRegex = /^\d{4}-\d{2}-\d{2}$/;
    return dateRegex.test(date);
  }

  startWorkPermissions() {
    const nivelesSeleccionados = this.niveles.filter(nivel => nivel.seleccionado);
    
    // Validar que todos los niveles seleccionados tengan fechas válidas
    const nivelesInvalidos = nivelesSeleccionados.filter(nivel => {
      return !this.validateDate(nivel.fechadesde) || 
             !this.validateDate(nivel.fechahasta);
    });

    if (nivelesInvalidos.length > 0) {
      alert('Todos los campos de fecha deben estar en formato AAAA-MM-DD para los niveles seleccionados');
      return;
    }

    this.iniciarPermisoTrabajoAlturasService.startWorkPermissions(nivelesSeleccionados).subscribe({
      next: () => {
        this.updateSuccess.set(true);
      },
      error: (err) => {
        console.error('Error al actualizar las fechas:', err);
        alert('Ocurrió un error al actualizar las fechas');
      },
    });
  }

  ngOnDestroy(): void {
    if (this.effectRef) {
      this.effectRef.destroy();
    }
  }
}
