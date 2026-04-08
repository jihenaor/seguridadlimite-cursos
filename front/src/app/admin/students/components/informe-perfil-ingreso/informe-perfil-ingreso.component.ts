import { Component, Input, OnInit } from '@angular/core';
import { ServicesService } from 'src/app/core/service/services.service';
import { MatIconModule } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu';

@Component({
    selector: 'aprendiz-informe-perfil-ingreso',
    templateUrl: './informe-perfil-ingreso.component.html',
    imports: [MatMenuModule, MatIconModule]
})
export class InformePerfilIngresoComponent implements OnInit {
  @Input()
  idaprendiz: number;

  constructor(public service: ServicesService,) { }

  ngOnInit(): void {
  }

  async showPdfPerfilingreso() {
//    this.loadFinish = !this.loadFinish;
    try {
      const r = await this.service.executeFetch('/' + this.idaprendiz + '/perfilingreso');
//      this.loadFinish = !this.loadFinish;

      const linkSource = 'data:application/pdf;base64,' + r.base64 + '\n';
      const downloadLink = document.createElement("a");
      const fileName = "perfilingreso.pdf";

      downloadLink.href = linkSource;
      downloadLink.download = fileName;
      downloadLink.click();
    } catch(error) {
//      this.loadFinish = !this.loadFinish;
    }
  }

}
