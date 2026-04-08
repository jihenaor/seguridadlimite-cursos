import { Injectable } from '@angular/core';

export interface TipoTrabajo {
  id: string;
  nombre: string;
}

@Injectable({
  providedIn: 'root'
})
export class TipoTrabajoService {
  private tiposTrabajo: TipoTrabajo[] = [
    { id: '1', nombre: 'Fachada' },
    { id: '2', nombre: 'Estructura' },
    { id: '3', nombre: 'Poste' },
    { id: '4', nombre: 'Réticula' },
    { id: '5', nombre: 'Cubierta' },
    { id: '6', nombre: 'Suspension' },
    { id: '7', nombre: 'Andamios' },
    { id: '8', nombre: 'Torres electrícas' }
  ];

  getTiposTrabajo(): TipoTrabajo[] {
    return this.tiposTrabajo;
  }

  getTipoTrabajoById(id: string): TipoTrabajo | undefined {
    return this.tiposTrabajo.find(tipo => tipo.id === id);
  }
}
