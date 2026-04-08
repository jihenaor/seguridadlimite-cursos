export class Bloque {
  id: number;
  nombre: string;
  constructor(nivel) {
    {
      this.id = nivel.id;
      this.nombre = nivel.nombre || '';
    }
  }
}
