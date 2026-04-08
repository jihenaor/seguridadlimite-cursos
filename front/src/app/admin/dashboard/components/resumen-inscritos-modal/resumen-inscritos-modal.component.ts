import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA, MatDialogModule } from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button';
import { NgIf, NgFor } from '@angular/common';
import { MatTableModule } from '@angular/material/table';

@Component({
    selector: 'app-resumen-inscritos-modal',
    imports: [MatDialogModule, MatButtonModule, MatTableModule],
    template: `
    <h2 mat-dialog-title>Resumen de Aprendices Inscritos</h2>
    <mat-dialog-content>
      <div class="resumen-stats">
        <p><strong>Total Inscritos:</strong> {{ data.aprendices.length }}</p>
        <p><strong>Pagados:</strong> {{ aprendicesPagados }}</p>
        <p><strong>Pendientes de Pago:</strong> {{ aprendicesPendientes }}</p>
      </div>

      <div class="resumen-por-nivel">
        <h3>Inscritos por Nivel</h3>
        <table class="table table-striped">
          <thead>
            <tr>
              <th>Nivel</th>
              <th>Total Inscritos</th>
              <th>Pagados</th>
              <th>Pendientes</th>
            </tr>
          </thead>
          <tbody>
            @for (nivel of nivelesDetallados; track nivel.nombre) {
              <tr>
                <td>{{ nivel.nombre }}</td>
                <td>{{ nivel.total }}</td>
                <td>{{ nivel.pagados }}</td>
                <td>{{ nivel.pendientes }}</td>
              </tr>
            }
            <tr class="table-info">
              <td><strong>Total</strong></td>
              <td><strong>{{ data.aprendices.length }}</strong></td>
              <td><strong>{{ aprendicesPagados }}</strong></td>
              <td><strong>{{ aprendicesPendientes }}</strong></td>
            </tr>
          </tbody>
        </table>
      </div>
    </mat-dialog-content>
    <mat-dialog-actions align="end">
      <button mat-button (click)="cerrar()">Cerrar</button>
    </mat-dialog-actions>
  `,
    styles: [`
    .resumen-stats {
      margin-bottom: 20px;
    }
    .resumen-por-nivel {
      margin-top: 20px;
    }
    table {
      width: 100%;
      margin-top: 1rem;
    }
    th, td {
      padding: 8px;
      text-align: left;
    }
    .table-info {
      background-color: #e3f2fd;
    }
  `]
})
export class ResumenInscritosModalComponent {
  aprendicesPagados: number = 0;
  aprendicesPendientes: number = 0;
  nivelesDetallados: any[] = [];

  constructor(
    public dialogRef: MatDialogRef<ResumenInscritosModalComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { aprendices: any[] }
  ) {
    this.calcularEstadisticas();
  }

  calcularEstadisticas() {
    this.aprendicesPagados = this.data.aprendices.filter(a => a.pagocurso === 'S').length;
    this.aprendicesPendientes = this.data.aprendices.length - this.aprendicesPagados;

    // Agrupar y calcular estadísticas por nivel
    const nivelesMap = new Map();

    this.data.aprendices.forEach(aprendiz => {
      const nivel = aprendiz.nivel.nombre;
      if (!nivelesMap.has(nivel)) {
        nivelesMap.set(nivel, {
          nombre: nivel,
          total: 0,
          pagados: 0,
          pendientes: 0
        });
      }

      const stats = nivelesMap.get(nivel);
      stats.total++;
      if (aprendiz.pagocurso === 'S') {
        stats.pagados++;
      } else {
        stats.pendientes++;
      }
    });

    this.nivelesDetallados = Array.from(nivelesMap.values());
  }

  cerrar(): void {
    this.dialogRef.close();
  }
}
