import { Component, Input, OnInit } from '@angular/core';
import { ServicesService } from 'src/app/core/service/services.service';
import { MatIconModule } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu';

@Component({
    selector: 'student-informe-formato-inscripcion',
    templateUrl: './informe-formato-inscripcion.component.html',
    imports: [MatMenuModule, MatIconModule]
})
export class InformeFormatoInscripcionComponent implements OnInit {
  @Input()
  idaprendiz: number;

  constructor(    public service: ServicesService,) { }

  ngOnInit(): void {
  }

  async showPdfFormatoinscripcion() {
//    this.loadFinish = !this.loadFinish;
    try {
      const r = await this.service.executeFetch('/' + this.idaprendiz + '/formatoinscripcion' );
//      this.loadFinish = !this.loadFinish;

      const linkSource = 'data:application/pdf;base64,' + r.base64 + '\n';
      const downloadLink = document.createElement("a");
      const fileName = "formatoinscripcion.pdf";

      downloadLink.href = linkSource;
      downloadLink.download = fileName;
      downloadLink.click();
    } catch(error) {
//      this.loadFinish = !this.loadFinish;
    }
  }

}
