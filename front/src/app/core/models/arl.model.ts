export class Arl {
  id: number;
  nombre: string;
  constructor(arl) {
    {
      this.id = arl.id;
      this.nombre = arl.nombre || '';
    }
  }
}
