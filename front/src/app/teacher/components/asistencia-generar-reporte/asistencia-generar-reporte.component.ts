import { ServicesService } from 'src/app/core/service/services.service';
import { Component, OnInit } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';

@Component({
    selector: 'teacher-asistencia-generar-reporte',
    templateUrl: './asistencia-generar-reporte.component.html',
    imports: [MatButtonModule]
})
export class AsistenciaGenerarReporteComponent implements OnInit {

  constructor(private service: ServicesService) { }

  ngOnInit(): void {
  }

  async showPdfAsistencia() {
    const r = await this.service.executeFetch('/aprendiz/asistenciainscritosreport');

    const linkSource = 'data:application/pdf;base64,' + r.base64 + '\n';
    const downloadLink = document.createElement("a");
    const fileName = "asistencia.pdf";

    downloadLink.href = linkSource;
    downloadLink.download = fileName;
    downloadLink.click();
  }
}
