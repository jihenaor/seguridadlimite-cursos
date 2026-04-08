import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';
import { TrabajadorFindService } from '../../../core/service/trabajadorfind.service';
import { DescargarCertificadoService } from '../../../core/service/descargarcertificado.service';
import { ShowNotificacionService } from '../../../core/service/show-notificacion.service';

@Component({
    selector: 'certificate-table',
    imports: [
        CommonModule,
        MatButtonModule,
        MatIconModule,
        MatCardModule
    ],
    template: `
    <div class="login100-more">
      @if (trabajadorfindService.trabajadors().length > 0) {
        <div class="worker-header mb-4 mx-auto" style="width: 70%;">
          <div class="card">
            <div class="card-body">
              <h5 class="card-title mb-3">Información del Trabajador</h5>
              <p class="card-text">
                <strong>Nombre:</strong>
                {{ trabajadorfindService.trabajadors()[0]?.nombreCompletoTrabajador }}
              </p>
            </div>
          </div>
        </div>

        <div class="table-container mx-auto" style="width: 70%;">
          <div class="card">
            <div class="body table-responsive">
              <table role="table" class="table table-hover">
                <thead role="rowgroup">
                  <tr>
                    <th role="columnheader" class="py-3">#</th>
                    <th role="columnheader" class="py-3">Curso</th>
                    <th role="columnheader" class="py-3">Código</th>
                    <th role="columnheader" class="py-3">Fecha Emisión</th>
                    <th role="columnheader" class="py-3">Acción</th>
                  </tr>
                </thead>
                <tbody>
                  <tr *ngFor="let aprendiz of trabajadorfindService.trabajadors(); let i = index">
                    <td role="cell" class="py-3">{{i + 1}}</td>
                    <td role="cell" class="py-3">{{ aprendiz.nivel?.nombre }}</td>
                    <td role="cell" class="py-3">{{ aprendiz.codigoverificacion }}</td>
                    <td role="cell" class="py-3">{{ aprendiz.fechaemision }}</td>
                    <td role="cell" class="py-3">
                      <div class="text-center">
                        @if (aprendiz.pagocurso === 'S') {
                          <button mat-raised-button color="primary"
                                  [disabled]="loading"
                                  (click)="descargarCertificadoPDF(aprendiz.id)">
                            <mat-icon>download</mat-icon>
                            <span class="ms-2">Descargar</span>
                          </button>
                        } @else {
                          <span class="text-danger">NO PAGADO</span>
                        }
                      </div>
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
      }
    </div>
  `
})
export class CertificateTableComponent {
  public trabajadorfindService = inject(TrabajadorFindService);
  loading = false;

  constructor(
    private descargarCertificadoService: DescargarCertificadoService,
    private notificacionService: ShowNotificacionService,
  ) {}

  descargarCertificadoPDF(idaprendiz: number) {
    this.loading = true;
    this.descargarCertificadoService.downloadCertificado(idaprendiz).subscribe({
      next: (reporte) => {
        this.downloadPDF(reporte, 'certificado.pdf');
        this.notificacionService.displaySuccess('Certificado descargado exitosamente');
        this.loading = false;
      },
      error: (error) => {
        this.loading = false;
        console.error('Error:', error);
        this.notificacionService.displayError(error.error.message);
      },
    });
  }

  private downloadPDF(reporte: any, fileName: string) {
    const linkSource = 'data:application/pdf;base64,' + reporte.base64 + '\n';
    const downloadLink = document.createElement('a');
    downloadLink.href = linkSource;
    downloadLink.download = fileName;
    downloadLink.click();
  }
}