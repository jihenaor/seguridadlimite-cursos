import { Nivel } from './nivel.model';
import { Grupo } from './grupo.model';

export class Programa {
  id: number;
  nombre: string;
  level: number;
  expandable: boolean;
  grupos: Grupo[];

  constructor(programa) {
    {
      this.id = programa.id;
      this.nombre = programa.nombre || '';
    }
  }
}
