import { Component, Input, OnInit, signal } from '@angular/core';
import { Aprendiz } from '../../../../core/models/aprendiz.model';
import { Asistencia } from '../../../../core/models/asistencia.model';
import { NgFor, NgIf } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { MatButtonModule } from '@angular/material/button';
import { RegistrarasistenciaService } from '../../../../teacher/services/registrarasistencia.service';

@Component({
    selector: 'aprendiz-asistencia',
    templateUrl: './consulta-asistencia.component.html',
    imports: [NgFor, NgIf, FormsModule, MatFormFieldModule, MatInputModule, MatSlideToggleModule, MatButtonModule]
})
export class ConsultaAsistenciaComponent implements OnInit {
  @Input()
  aprendiz: Aprendiz;
  
  fechaSeleccionada: string = '';
  isLoading = signal(false);
  errorMessage = signal('');
  
  constructor(private registrarasistenciaService: RegistrarasistenciaService) { }

  ngOnInit(): void {
  }

  totalizarHoras() {
    let horas = 0;

    this.aprendiz?.asistencias?.forEach((asistencia) => {
      horas += asistencia.horas;
    });

    return horas;
  }

  onChangeAsistencia(asistencia: Asistencia) {
    const dateRegex = /^\d{4}-\d{2}-\d{2}$/;
    if (!dateRegex.test(this.fechaSeleccionada)) {
      console.error('Fecha de asistencia no válida ' + this.fechaSeleccionada);
      this.errorMessage.set('Fecha no válida');
      return;
    }
    try {
      asistencia.selected = !asistencia.selected;
      asistencia.fecha = this.fechaSeleccionada;
      asistencia.idaprendiz = this.aprendiz.id;

      this.isLoading.set(true);
      this.errorMessage.set('');

      this.registrarasistenciaService.registrar(asistencia).subscribe({
        next: (response) => {
          console.log('Asistencia registrada:', response);
          this.isLoading.set(false);
        },
        error: (error) => {
          console.error('Error al registrar asistencia:', error);
          this.errorMessage.set(error.message || 'Error al registrar la asistencia');
          this.isLoading.set(false);
          // Revertir el cambio en caso de error
          asistencia.selected = !asistencia.selected;
        }
      });

    } catch (error: any) {
      console.error('Error en el procesamiento de la fecha:', error);
      this.errorMessage.set(error.message || 'Error al procesar la fecha');
      // Revertir el cambio en caso de error
      asistencia.selected = !asistencia.selected;
    }
  }
}
