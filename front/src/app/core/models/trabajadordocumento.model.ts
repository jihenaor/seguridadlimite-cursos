export class Trabajadordocumento {
  id: number;
  ext: string;
  base64a: string;
  base64b: string;
  nombrearchivoa: string;
  nombrearchivob: string;
  constructor(trabajador) {
    {
      this.id = trabajador.id;
      this.ext = trabajador.ext;
      this.base64a = trabajador.base64a;
      this.base64b = trabajador.base64b;
    }
  }
}
