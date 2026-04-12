import { Component, Input, OnInit } from '@angular/core';
import { ServicesService } from '../../../../core/service/services.service';
import { MatIconModule } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu';

@Component({
    selector: 'app-formato-evaluacion',
    templateUrl: './formato-evaluacion.component.html',
    imports: [MatIconModule, MatMenuModule]
})
export class FormatoEvaluacionComponent implements OnInit {
  @Input()
  idaprendiz: string;

  public loadFinish = false;

  constructor(
    public service: ServicesService,
  ) { }

  ngOnInit(): void {
  }


  async showPdfFormatoEvaluacion() {
    this.loadFinish = !this.loadFinish;
    try {
      const r = await this.service.executeFetch('/formatoevaluacion/' + this.idaprendiz);
      this.loadFinish = !this.loadFinish;

      const linkSource = 'data:application/pdf;base64,' + r.base64 + '\n';
      const downloadLink = document.createElement("a");
      const fileName = "perfilingreso.pdf";

      downloadLink.href = linkSource;
      downloadLink.download = fileName;
      downloadLink.click();
    } catch(error) {
      this.loadFinish = !this.loadFinish;
    }
  }


}
