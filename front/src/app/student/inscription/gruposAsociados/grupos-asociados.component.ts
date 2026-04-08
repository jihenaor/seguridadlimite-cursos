import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { RegistrarContinuaAprendizajeService } from '../../../core/service/registrar-continua-aprendizaje.service';

import { Trabajador } from '../../../core/models/trabajador.model';
import { ShowNotificacionService } from 'src/app/core/service/show-notificacion.service';

@Component({
    selector: 'app-grupos-asociados',
    templateUrl: './grupos-asociados.component.html',
    styleUrl: './grupos-asociados.component.scss',
    imports: [
        CommonModule,
        MatIconModule,
        MatButtonModule
    ]
})
export class GruposAsociadosComponent implements OnInit {
  @Input() trabajador: Trabajador;
  @Output() trabajadorChange = new EventEmitter<any>();
  public actualizacionGrupoExitosa = false;
  public continuacionRegistrada = false;

  constructor(
    public registrarContinuaService: RegistrarContinuaAprendizajeService,
    private notificacionService: ShowNotificacionService
  ) {}

  ngOnInit() {}


  async continuarAprendizaje() {
    try {
      await this.registrarContinuaService.registrarContinuacionAprendizaje(this.trabajador.idaprendiz);

      this.continuacionRegistrada = true;
      this.notificacionService.displaySuccess('Registro exitoso de continuación de aprendizaje');

      this.trabajador.aprendizContinuaAprendizaje = true;
      this.trabajadorChange.emit(this.trabajador);
    } catch (error) {
      console.error('Error al continuar aprendizaje:', error);
      this.notificacionService.displayError(
        error.message || 'Ocurrió un error al registrar la continuación'
      );
    }
  }
}
