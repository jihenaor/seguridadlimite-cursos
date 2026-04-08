import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { Aprendiz } from 'src/app/core/models/aprendiz.model';
import { Grupo } from 'src/app/core/models/grupo.model';

@Injectable({
  providedIn: 'root'
})
export class ComunicacionService {
  private seleccionAprendicesSinGrupo = new Subject<boolean>();
  private grupoCambiado = new Subject<Grupo>();
  private aprendizCambiado = new Subject<Aprendiz>();

  seleccionAprendicesSinGrupo$ = this.seleccionAprendicesSinGrupo.asObservable();
  grupoCambiado$ = this.grupoCambiado.asObservable();

  aprendizCambiado$ = this.aprendizCambiado.asObservable();

  informarSeleccionAprendicesSinGrupo() {
    this.seleccionAprendicesSinGrupo.next(true);
  }

  informarCambioGrupo(grupo: Grupo) {
    this.grupoCambiado.next(grupo);
  }

  informarCambioAprendiz(aprendiz: Aprendiz) {
    this.aprendizCambiado.next(aprendiz);
  }
}
