import { Component, Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AprendizFindService } from '../../../../../core/service/aprendizfind.service';
import { Aprendiz } from '../../../../../core/models/aprendiz.model';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-aprendiz-permiso-trabajo',
  standalone: true,
  imports: [
    CommonModule,
    MatButtonModule,
    MatIconModule,
  ],
  templateUrl: './aprendiz-permiso-trabajo.component.html',
  styleUrls: ['./aprendiz-permiso-trabajo.component.sass']
})
export class AprendizPermisoTrabajoComponent implements OnInit {
  @Input() idPermiso!: number;
  aprendices: Aprendiz[] = [];

  constructor(
    private aprendizFindService: AprendizFindService,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    
  }

  buscarAprendices(): void {
    this.aprendizFindService.searchAprendizByIdpermiso(this.idPermiso)
      .subscribe({
        next: (aprendices) => {
          this.aprendices = aprendices;
        },
        error: (error) => {
          this.snackBar.open('Error al buscar aprendices', 'Cerrar', { duration: 3000 });
        }
      });
  }

  buscarAprendicesUltimaAsistencia(): void {
    this.aprendizFindService.searchAprendizUltimaAsistenciaByIdpermiso(this.idPermiso)
      .subscribe({
        next: (aprendices) => {
          this.aprendices = aprendices;
        },
        error: (error) => {
          this.snackBar.open('Error al buscar aprendices', 'Cerrar', { duration: 3000 });
        }
      });
  }

  buscarAprendicesInscritos(): void {
    this.aprendizFindService.searchAprendizInscritosByIdpermiso(this.idPermiso)
      .subscribe({
        next: (aprendices) => {
          this.aprendices = aprendices;
        },
        error: (error) => {
          this.snackBar.open('Error al buscar aprendices', 'Cerrar', { duration: 3000 });
        }
      });
  }

  asignarPermiso(aprendiz: Aprendiz): void {
    if (!aprendiz.idPermiso) {
      this.aprendizFindService.updateAprendizIdPermiso(aprendiz.id, this.idPermiso)
        .subscribe({
          next: () => {
            this.snackBar.open('Permiso asignado correctamente', 'Cerrar', {
              duration: 3000
            });
            this.buscarAprendices();
          }
        });
    }
  }

  getNivelEducativo(nivel: string): string {
    const niveles = {
      '1': 'Primaria',
      '2': 'Bachillerato',
      '3': 'Técnico',
      '4': 'Tecnólogo',
      '5': 'Universitaria',
      '6': 'Tecnólogo'
    };
    return niveles[nivel] || '--';
  }
}
