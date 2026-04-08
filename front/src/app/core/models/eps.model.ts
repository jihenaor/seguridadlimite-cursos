export class Eps {
  id: number;
  nombre: string;
  constructor(eps) {
    {
      this.id = eps.id;
      this.nombre = eps.nombre || '';
    }
  }
}
