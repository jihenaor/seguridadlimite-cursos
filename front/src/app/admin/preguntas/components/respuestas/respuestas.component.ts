import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Pregunta } from 'src/app/core/models/pregunta.model';
import { Respuesta } from 'src/app/core/models/respuesta.model';
import { NgIf, NgFor, NgStyle } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { SvgIconComponent } from '../../../../shared/components/svg-icon/svg-icon.component';
import { PreguntasRegistrarService } from '../../all-preguntas/preguntaregistrar.service';
import { ShowNotificacionService } from '../../../../core/service/show-notificacion.service';

@Component({
    selector: 'app-respuestas',
    templateUrl: './respuestas.component.html',
    imports: [NgIf, NgFor, NgStyle, MatButtonModule, SvgIconComponent],
})
export class RespuestasComponent {
  @Input()
  pregunta: Pregunta;

  @Output()
  readonly respuestaDeleted = new EventEmitter<void>();

  constructor(
    private preguntasRegistrarService: PreguntasRegistrarService,
    private notificacionService: ShowNotificacionService
  ) {}

  eliminarRespuesta(respuesta: Respuesta, event: MouseEvent): void {
    event.stopPropagation();
    if (respuesta.id == null) {
      return;
    }
    if (
      !globalThis.confirm(
        '¿Eliminar esta respuesta? Se borrarán las evaluaciones registradas para esa opción de respuesta.'
      )
    ) {
      return;
    }
    this.preguntasRegistrarService.deleteRespuesta(this.pregunta.id, respuesta.id).subscribe({
      next: () => {
        this.notificacionService.displaySuccess('Respuesta eliminada');
        this.respuestaDeleted.emit();
      },
    });
  }
}
