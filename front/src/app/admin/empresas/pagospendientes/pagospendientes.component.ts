import { ShowNotificacionService } from './../../../core/service/show-notificacion.service';
import { Component, OnInit } from '@angular/core';

import { PagopendienteempresaService } from './pagospendientes.service';
import { Pagopendienteempresa } from 'src/app/core/models/pagopendienteempresa.model';
import { RegistrarpagopendienteempresaService } from './../../../core/service/registrarpagopendienteempresa.service';
import { tap } from 'rxjs';
import { Aprendiz } from '../../../core/models/aprendiz.model';
import { MatIconModule } from '@angular/material/icon';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatButtonModule } from '@angular/material/button';
import { NgFor, NgIf } from '@angular/common';
import { PagetitleComponent } from '../../../shared/components/page-title/pagetitle.component';

@Component({
    selector: 'app-pagospendientes',
    templateUrl: './pagospendientes.component.html',
    imports: [
        PagetitleComponent,
        NgFor,
        MatButtonModule,
        MatTooltipModule,
        MatIconModule,
        NgIf,
    ]
})
export class PagospendientesComponent implements OnInit {
  preguntaDetalle: number = -1;

  constructor(
    public pagopendienteempresaService: PagopendienteempresaService,
    private registrarpagopendienteempresaService: RegistrarpagopendienteempresaService,
    private notificacionService: ShowNotificacionService
  ) {}

  ngOnInit() {
    this.loadData();
  }

  public loadData() {
    this.pagopendienteempresaService.getPagospendientesempresa();
  }

  toggleDetail(index: number) {
    if (this.preguntaDetalle === index) {
      this.preguntaDetalle = -1; // Oculta el detalle si ya está visible
    } else {
      this.preguntaDetalle = index; // Muestra el detalle de la pregunta seleccionada
    }
  }

  registerPayment(pagopendienteempresa: Pagopendienteempresa) {
    this.registrarpagopendienteempresaService.register(pagopendienteempresa)
    .pipe(
      tap(() => {
        this.notificacionService.displaySuccess('Actualización exitosa');
        this.loadData();
      })
    )
    .subscribe();
  }

  registerPaymentAprendiz(aprendiz: Aprendiz) {
    this.registrarpagopendienteempresaService.registerAprendiz(aprendiz)
    .pipe(
      tap(() => {
        this.notificacionService.displaySuccess('Actualización exitosa');
        this.loadData();
      })
    )
    .subscribe();
  }
}

